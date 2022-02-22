package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.request.ApplyInterviewRequest
import com.appsolute.soomapi.domain.chat.data.request.ConcludeInterviewRequest
import com.appsolute.soomapi.domain.chat.data.type.InterviewResultType
import com.appsolute.soomapi.domain.chat.repository.MessageCountRepository
import com.appsolute.soomapi.domain.chat.service.InterviewService
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.entity.JoinRejectedUser
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.group.GroupInfoRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.repository.post.PostRepository
import com.appsolute.soomapi.domain.soom.repository.group.RejectedUserRepository
import com.appsolute.soomapi.domain.soom.repository.post.NoticeRepository
import com.appsolute.soomapi.domain.soom.repository.post.ReplyRepository
import com.appsolute.soomapi.domain.soom.util.CheckGroupUtil
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class ManageMemberServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val rejectedUserRepository: RejectedUserRepository,
    private val interviewService: InterviewService,
    private val userRepository: UserRepository,
    private val groupInfoRepository: GroupInfoRepository,
    private val replyRepository: ReplyRepository,
    private val check: CheckGroupUtil,
    private val messageCountRepository: MessageCountRepository,
    private val noticeRepository: NoticeRepository

): ManageMemberService {


    @Transactional
    override fun getJoinRequestList(groupId: String): List<UserResponse>{
        return check.checkIsGroupMember(groupId, current.getPk()).soom.joinRequestMemberList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    @Transactional
    override fun isJoinRequestSent(groupId: String): Boolean{
        return groupRepository.findById(groupId).map {
            it.memberList.contains(current.getUser())
        }.orElse(null)?: return false

    }

    @Transactional
    override fun sendJoinRequest(groupId: String){
        val group = check.checkIsNotGroupMember(groupId).soom
        val user = current.getUser()
        val gt = group.type

        if (user.getRole().equals(Role.TEACHER)) {
            if (gt.equals(GroupType.CLUB_CA)||gt.equals(GroupType.CLUB_ETC)||
                gt.equals(GroupType.CLUB_MAJOR)||gt.equals(GroupType.TEAM)) {
                group.teacher?.let {
                    throw TeacherAlreadyExistsException(it.getName())
                } ?: let {
                    group.teacher = current.getTeacher()
                }
            } else {
                group.teacher?.let {
                    group.memberList.add(user)
                }?: let {
                    group.teacher = user as Teacher
                }
            }
        } else if (rejectedUserRepository.findById(groupId + user.uuid).isPresent) throw RejectedUserException(
            user.uuid
        )
        else {
            if (!group.type.equals(GroupType.CLASS)||!group.type.equals(GroupType.COUNSIL)) interviewService.applyInterview(ApplyInterviewRequest(group))
            group.addJoinRequestMemberList(user!! as Student)
        }

        groupRepository.save(group)
    }

    @Transactional
    override fun receiveJoinRequest(groupId: String, receiverId: String, message: String){
        val dto = check.checkIsNotGroupMember(groupId, receiverId)
        val accepted = dto.user
        if (!dto.soom.type.equals(GroupType.CLASS)||!dto.soom.type.equals(GroupType.COUNSIL)) {
            interviewService.concludeInterview(
                ConcludeInterviewRequest(
                    message,
                    InterviewResultType.ACCEPT,
                    dto.soom,
                    accepted
                )
            )
        }
        dto.soom.acceptJoinRequest(accepted)
    }

    @Transactional
    override fun receiveEveryJoinRequest(groupId: String, message: String){
        val group = check.checkIsGroupHeader(groupId).soom
        if (!group.type.equals(GroupType.CLASS)||!group.type.equals(GroupType.COUNSIL)) {
            group.joinRequestMemberList.stream().map {
                interviewService.concludeInterview(
                    ConcludeInterviewRequest(
                        message,
                        InterviewResultType.ACCEPT,
                        group,
                        it
                    )
                )
            }
        }
        group.acceptAllJoinRequest()
    }

    @Transactional
    override fun rejectJoinRequest(groupId: String, studentId: String, message: String){
        val group = check.checkIsGroupHeader(groupId).soom
        val student = userRepository.findById(studentId).orElse(null)?: throw UserNotFoundException(studentId)
        if (!group.type.equals(GroupType.CLASS)||!group.type.equals(GroupType.COUNSIL)) {
            interviewService.concludeInterview(
                ConcludeInterviewRequest(
                    message,
                    InterviewResultType.REJECT,
                    group,
                    student
                )
            )
        }
        group.rejectJoinRequest(student)
        rejectedUserRepository.save(
            JoinRejectedUser(
                groupId,
                studentId
            )
        )
    }

    @Transactional
    override fun rejectAllJoinRequest(groupId: String, message: String){
        val group: Soom = check.checkIsGroupHeader(groupId).soom
        if (!group.type.equals(GroupType.CLASS)||!group.type.equals(GroupType.COUNSIL)) {
            group.joinRequestMemberList.stream().map {
                interviewService.concludeInterview(
                    ConcludeInterviewRequest(
                        message,
                        InterviewResultType.REJECT,
                        group,
                        it
                    )
                )
            }
        }
        val joinRejectedUserLists: MutableList<JoinRejectedUser> = ArrayList<JoinRejectedUser>()
        group.rejectAllJoinRequest().stream().map {
            joinRejectedUserLists.add(JoinRejectedUser(groupId, it.uuid ))
        }
        rejectedUserRepository.saveAll(joinRejectedUserLists)
    }

    @Transactional
    override fun getGroupMemberList(groupId: String): List<UserResponse>{
        return groupRepository.findById(groupId).map {
            it.memberList.stream().map {
                it.toUserResponse()
            }.toList()
        }.orElse(null)?: throw GroupCannotFoundException(groupId)
    }

    @Transactional
    override fun getGroupMember(groupId: String, userId: String): GroupUserResponse {
        val group = groupRepository.findById(groupId).map {
            it
        }.orElse(null)?: throw GroupCannotFoundException(groupId)

        val user: User = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException(userId)
        val groupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, user).orElse(null)?: throw GroupCannotFoundException(groupId)

        return GroupUserResponse(
            user.toUserResponse(),
            groupInfo.joinedAt ,
            noticeRepository.findAllByGroupAndWriter(group, user, PageRequest.of(1, 10)).stream().map {
                it.toShortnessNoticeResponse()
            }.toList(),
            replyRepository.findAllByGroupAndWriter(group, user, PageRequest.of(1, 10)).stream().map {
                it.toShortnessReplyResponse()
            }.toList(),
            groupInfo.auth
        )
    }

    @Transactional
    override fun kickGroupMember(groupId: String, userId: String){
        val member = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException(userId)

        val group = check.checkIsGroupMember(groupId, userId).soom
        val groupInfo = groupInfoRepository.findById(userId + groupId + "groupInfo").orElse(null)?: throw GroupCannotFoundException(groupId)

        groupRepository.save(
            group.kickMember(
                member,
                groupInfo
            )
        )
        userRepository.save(member)
        groupInfoRepository.delete(groupInfo)
        messageCountRepository.delete(
            messageCountRepository.findById(userId + groupId).get()
        )

    }

    @Transactional
    override fun getDeleteRequestUser(groupId: String): List<UserResponse>{
        return check.checkIsGroupMember(groupId).soom.deleteVoterList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    @Transactional
    override fun getOutGroup(groupId: String): List<ShortnessGroupResponse> {
        val user = current.getUser()
        kickGroupMember(groupId, user.uuid)
        return user.groupInfo.stream().map {
            it.group.toShortnessGroupResponse()
        }.toList()
    }

}
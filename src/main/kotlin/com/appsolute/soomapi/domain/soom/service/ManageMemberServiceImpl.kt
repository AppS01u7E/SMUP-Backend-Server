package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.repository.MessageCountRepository
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.entity.RejectedUser
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.group.GroupInfoRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.repository.post.PostRepository
import com.appsolute.soomapi.domain.soom.repository.group.RejectedUserRepository
import com.appsolute.soomapi.domain.soom.util.CheckGroupUtil
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.swing.GroupLayout
import javax.transaction.Transactional


@Service
class ManageMemberServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val rejectedUserRepository: RejectedUserRepository,
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository,
    private val groupInfoRepository: GroupInfoRepository,
    private val postRepository: PostRepository,
    private val check: CheckGroupUtil,
    private val messageCountRepository: MessageCountRepository

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
            group.addJoinRequestMemberList(user!! as Student)
        }

        groupRepository.save(group)
    }

    @Transactional
    override fun receiveJoinRequest(groupId: String, receiverId: String){
        val dto = check.checkIsNotGroupMember(groupId, receiverId)
        val accepted = dto.user

        dto.soom.acceptJoinRequest(accepted)
    }

    @Transactional
    override fun receiveEveryJoinRequest(groupId: String){
        val group = check.checkIsGroupHeader(groupId).soom
        group.acceptAllJoinRequest()
    }

    @Transactional
    override fun rejectJoinRequest(groupId: String, studentId: String){
        val group = check.checkIsGroupHeader(groupId).soom
        val student = userRepository.findById(studentId).orElse(null)?: throw UserNotFoundException(studentId)
        group.rejectJoinRequest(student)
        rejectedUserRepository.save(
            RejectedUser(
                groupId,
                studentId
            )
        )
    }

    @Transactional
    override fun rejectAllJoinRequest(groupId: String){
        val group: Soom = check.checkIsGroupHeader(groupId).soom
        var rejectedUserList: MutableList<RejectedUser> = ArrayList<RejectedUser>()
        group.rejectAllJoinRequest().stream().map {
            rejectedUserList.add(RejectedUser(groupId, it.uuid ))
        }
        rejectedUserRepository.saveAll(rejectedUserList)
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
            postRepository.findAllByGroupAndPostType(group, PostType.NOTICE, PageRequest.of(1, 10)).toList(),
            postRepository.findAllByGroupAndPostType(group, PostType.REPLY, PageRequest.of(1, 10)).toList(),
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
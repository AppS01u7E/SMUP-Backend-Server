package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.repository.MessageCountRepository
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.entity.RejectedUser
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse
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


    override fun getJoinRequestList(groupId: String): List<UserResponse>{
        return check.checkIsGroupMember(groupId, current.getPk()).soom.memberList.stream().map {
            it.toUserResponse()
        }.toList()
    }






    override fun isJoinRequestSent(groupId: String): Boolean{
        return groupRepository.findById(groupId).map {
            it.memberList.contains(current.getUser())
        }.orElse(null)?: return false

    }

    override fun sendJoinRequest(groupId: String){
        val group = check.checkIsNotGroupMember(groupId).soom
        if (current.getUser().getRole().equals(Role.TEACHER)) {
            group.teacher?.let {
                throw TeacherAlreadyExistsException(it.getName())
            }?: let {
                group.teacher = current.getTeacher()
            }
        }
        else if (rejectedUserRepository.findById(groupId + current.getUser().id).isPresent) throw RejectedUserException(current.getPk())
        else {
            group.addJoinRequestMemberList(current.getStudent()!!)
        }
        groupRepository.save(group)
    }

    override fun receiveJoinRequest(groupId: String, receiverId: String){
        val accepted = studentRepository.findById(receiverId).map{
            it
        }.orElse(null)?: throw UserNotFoundException(receiverId)

        (groupRepository.findById(groupId).map{
            {
                if (!it.joinRequestMemberList.contains(accepted)) throw HasNotJoinRequestException(receiverId)
                else {
                    it.approveJoinRequest(accepted)
                    groupRepository.save(it)
                    userRepository.save(accepted)
                }
            }
        }.orElse(null)?: throw GroupCannotFoundException(groupId))

    }

    @Transactional
    override fun receiveEveryJoinRequest(groupId: String){

        val group: Soom = groupRepository.findByIdAndMemberListContains(groupId, current.getUser())
            .orElse(null)?: throw GroupCannotFoundException(groupId)

        group.acceptAllJoinRequest()

    }

    override fun rejectJoinRequest(groupId: String, studentId: String){
        rejectedUserRepository.save(
            RejectedUser(
                groupId,
                studentId
            )
        )
    }

    @Transactional
    override fun rejectAllJoinRequest(groupId: String){
        val group: Soom = groupRepository.findByIdAndMemberListContains(groupId, current.getUser())
            .orElse(null)?: throw GroupCannotFoundException(groupId)
        group.rejectAllJoinRequest()

    }





    override fun getGroupMemberList(groupId: String): List<UserResponse>{
        return groupRepository.findById(groupId).map {
            it.memberList.stream().map {
                it.toUserResponse()
            }.toList()
        }.orElse(null)?: throw GroupCannotFoundException(groupId)
    }

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

    override fun getDeleteRequestUser(groupId: String): List<UserResponse>{
        return check.checkIsGroupMember(groupId).soom.deleteVoterList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    override fun getOutGroup(groupId: String): List<ShortnessGroupResponse> {
        val user = current.getUser()
        kickGroupMember(groupId, user.id)
        return user.groupInfo.stream().map {
            it.group.toShortnessGroupResponse()
        }.toList()
    }

}
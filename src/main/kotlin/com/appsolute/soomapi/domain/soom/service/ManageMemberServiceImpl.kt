package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.entity.RejectedUser
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.group.GroupInfoRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.repository.post.PostRepository
import com.appsolute.soomapi.domain.soom.repository.group.RejectedUserRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest

class ManageMemberServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val rejectedUserRepository: RejectedUserRepository,
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository,
    private val groupInfoRepository: GroupInfoRepository,
    private val postRepository: PostRepository

): ManageMemberService {


    override fun getJoinRequest(groupId: String): List<UserResponse>{
        return checkIsGroupMember(groupId, current.getPk()).memberList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    override fun isJoinRequestSent(groupId: String): Boolean{
        return groupRepository.findById(groupId).map {
            it.memberList.contains(current.getUser())
        }.orElse(null)?: return false

    }

    override fun sendJoinRequest(groupId: String){
        val group = checkIsNotGroupMember(groupId)
        if (current.getUser().getRole().equals(Role.TEACHER)) throw TeacherAlreadyExistsException()
        else if (rejectedUserRepository.findById(groupId + current.getUser().id).isPresent) throw RejectedUserException()
        else {
            group.addJoinRequestMemberList(current.getStudent())
        }
        groupRepository.save(group)
    }

    override fun receiveJoinRequest(groupId: String, receiverId: String){
        val accepted = studentRepository.findById(receiverId).map{
            it
        }.orElse(null)?: throw UserNotFoundException()

        (groupRepository.findById(groupId).map{
            {
                if (!it.joinRequestMemberList.contains(accepted)) throw HasNotJoinRequestException()
                else {
                    it.approveJoinRequest(accepted)
                    groupRepository.save(it)
                    userRepository.save(accepted)
                }
            }
        }.orElse(null)?: throw GroupCannotFoundException())

    }

    override fun receiveEveryJoinRequest(groupId: String, receiverIds: List<String>){
        receiverIds.forEach { receiveJoinRequest(groupId, it) }
    }

    override fun rejectJoinRequest(groupId: String, studentId: String){
        rejectedUserRepository.save(
            RejectedUser(
                groupId,
                studentId
            )
        )
    }

    override fun rejectAllJoinRequest(groupId: String, studentIds: List<String>){
        studentIds.forEach {
            rejectJoinRequest(groupId, it)
        }
    }

    private fun checkIsNotGroupMember(groupId: String): Group {
        return groupRepository.findById(groupId).map{
            if (it.memberList.contains(current.getUser())){
                throw IsAlreadyGroupMmeberException()
            }
            else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    private fun checkIsGroupMember(groupId: String, userId: String): Group {
        return groupRepository.findById(groupId).map {
            if (!it.memberList.contains(userRepository.findById(userId).orElse(null)
                    ?: throw UserNotFoundException()
                )) {
                throw IsNotGroupMemberException()
            } else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }




    override fun getGroupMemberList(groupId: String): List<UserResponse>{
        return groupRepository.findById(groupId).map {
            it.memberList.stream().map {
                it.toUserResponse()
            }.toList()
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    override fun getGroupMember(groupId: String, userId: String): GroupUserResponse {
        val group = groupRepository.findById(groupId).map {
            it
        }.orElse(null)?: throw GroupCannotFoundException()

        val user: User = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException()
        val groupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, user).orElse(null)?: throw GroupCannotFoundException()

        return GroupUserResponse(
            user.toUserResponse(),
            groupInfo.joinedAt ,
            postRepository.findAllByGroupAndPostType(group, PostType.NOTICE, PageRequest.of(1, 10)).toList(),
            postRepository.findAllByGroupAndPostType(group, PostType.REPLY, PageRequest.of(1, 10)).toList(),
            groupInfo.auth
        )
    }

    override fun transferAuthority(groupId: String, userId: String){
        val user = userRepository.findById(userId)
            .orElse(null)?: throw UserNotFoundException()
        val group = checkIsGroupHeader(groupId)

        val targetGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, user)
            .orElse(null)?: throw GroupCannotFoundException()
        val actorGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, current.getUser())
            .orElse(null)?: throw GroupCannotFoundException()

        groupInfoRepository.save(targetGroupInfo.changeAuth(actorGroupInfo.auth))
        groupInfoRepository.save(actorGroupInfo.removeAuth())
    }


    override fun kickGroupMember(groupId: String, userId: String){
        val member = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException()
        val group = checkIsGroupMember(groupId, userId)
        groupRepository.save(
            group.kickMember(
                member,
                groupInfoRepository.findByGroupAndUser(group, member).orElse(null)
                    ?: throw GroupCannotFoundException()
            )
        )

    }

    private fun checkIsGroupHeader(groupId: String): Group {
        return groupRepository.findById(groupId).map {
            if (!it.header.equals(current.getUser())) {
                throw LowerAuthException()
            } else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }


}
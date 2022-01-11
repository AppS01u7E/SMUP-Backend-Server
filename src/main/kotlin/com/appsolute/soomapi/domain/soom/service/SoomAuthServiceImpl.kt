package com.appsolute.soomapi.domain.soom.service

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolulte.smupbackendserver.domain.account.repository.StudentRepository
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.request.WriteReplyRequest
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.entity.*
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.*
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.streams.toList


class SoomAuthServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val userRepository: UserRepository
    // DI 받는 곳
) {



    fun setSubHeader(groupId: String, memberId: String) {
        var member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        var group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.setSubHeader(member)

        groupRepository.save(group)
    }

    fun removeSubHeader(groupId: String, memberId: String){
        val member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        val group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.dismissalSubHeader(member)

        groupRepository.save(group)
    }


}
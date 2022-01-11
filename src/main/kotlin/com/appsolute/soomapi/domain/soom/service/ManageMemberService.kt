package com.appsolute.soomapi.domain.soom.service

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.Role
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.entity.RejectedUser
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.*
import org.springframework.data.domain.PageRequest

interface ManageMemberService {

    fun getJoinRequest(groupId: String): List<UserResponse>

    fun isJoinRequestSent(groupId: String): Boolean

    fun sendJoinRequest(groupId: String)

    fun receiveJoinRequest(groupId: String, receiverId: String)

    fun receiveEveryJoinRequest(groupId: String, receiverIds: List<String>)

    fun rejectJoinRequest(groupId: String, studentId: String)

    fun rejectAllJoinRequest(groupId: String, studentIds: List<String>)

    fun getGroupMemberList(groupId: String): List<UserResponse>

    fun getGroupMember(groupId: String, userId: String): GroupUserResponse

    fun transferAuthority(groupId: String, userId: String)

    fun kickGroupMember(groupId: String, userId: String)
}
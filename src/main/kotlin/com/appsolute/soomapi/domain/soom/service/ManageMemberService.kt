package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse

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
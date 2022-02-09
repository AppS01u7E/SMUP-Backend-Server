package com.appsolute.soomapi.domain.chat.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.ShortnessUserResponse
import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.chat.data.type.ChatRoomType
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse

data class ChatRoomResponse (
    val id: String,
    val name: String,
    val type: ChatRoomType,
    val profile: String?,
    val memberCount: Int,
    val admin: ShortnessUserResponse,
    val group: ShortnessGroupResponse?,
    val unreadCount: Int, //99보다 많을 시 99+
    val isDone: Boolean

)
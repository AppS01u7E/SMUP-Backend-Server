package com.appsolute.soomapi.domain.chat.data.response

data class ChatRoomListResponse(
    val total: Int,
    val chatRoomList: MutableList<ChatRoomResponse>
)
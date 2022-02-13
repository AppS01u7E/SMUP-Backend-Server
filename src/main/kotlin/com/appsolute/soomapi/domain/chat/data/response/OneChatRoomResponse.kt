package com.appsolute.soomapi.domain.chat.data.response

data class OneChatRoomResponse(
    val page: Int,
    val size: Int,
    val messageList: List<HttpMessageResponse>,
    val chatRoomId: String

)

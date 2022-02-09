package com.appsolute.soomapi.domain.chat.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.File

data class DetailChatRoomResponse(
    val memberList: List<UserResponse>,
    val files: List<File>?,
    val messageList: List<HttpMessageResponse>

)

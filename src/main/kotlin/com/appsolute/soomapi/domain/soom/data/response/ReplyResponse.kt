package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.type.ReplyType

data class ReplyResponse (
    val id: String,
    val content: String,
    val writer: UserResponse,
    val sendToId: String
)
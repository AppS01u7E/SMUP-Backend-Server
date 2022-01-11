package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse

data class ReplyResponse (
    var id: String,
    var content: String,
    var writer: UserResponse,
    var sendToId: String
)
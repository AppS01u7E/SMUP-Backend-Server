package com.appsolute.soomapi.global.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.deprecated.UserResponse

data class ReplyResponse (
    var id: String,
    var content: String,
    var writer: UserResponse,
    var sendToId: String
)
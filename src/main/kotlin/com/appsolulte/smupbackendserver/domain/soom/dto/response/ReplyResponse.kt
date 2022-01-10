package com.appsolulte.smupbackendserver.domain.soom.dto.response

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse

data class ReplyResponse (
    var id: String,
    var content: String,
    var writer: UserResponse,
    var sendToId: String
)
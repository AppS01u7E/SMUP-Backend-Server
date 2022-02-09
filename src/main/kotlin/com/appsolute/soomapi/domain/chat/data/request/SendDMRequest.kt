package com.appsolute.soomapi.domain.chat.data.request

import com.appsolute.soomapi.domain.chat.data.type.MessageType

data class SendDMRequest(
    val message: String,
    val targetId: String,
    val messageType: MessageType

)

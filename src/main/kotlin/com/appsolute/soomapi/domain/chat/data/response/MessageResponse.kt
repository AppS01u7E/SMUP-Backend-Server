package com.appsolute.soomapi.domain.chat.data.response

import com.appsolute.soomapi.domain.chat.data.type.MessageType
import java.time.LocalDateTime

abstract class MessageResponse (
    val messageId: String,
    val sender: Sender?,
    val content: String,
    val sentAt: LocalDateTime,
    val messageType: MessageType

){
    data class Sender(
        val name: String,
        val id: String
    )
}

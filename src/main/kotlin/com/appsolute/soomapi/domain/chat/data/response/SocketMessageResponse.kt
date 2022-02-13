package com.appsolute.soomapi.domain.chat.data.response

import com.appsolute.soomapi.domain.chat.data.type.MessageType
import java.time.LocalDateTime

class SocketMessageResponse(
    messageId: String,
    sender: Sender?,
    content: String,
    sentAt: LocalDateTime,
    messageType: MessageType

): MessageResponse(
    messageId,
    sender,
    content,
    sentAt,
    messageType
) {

}
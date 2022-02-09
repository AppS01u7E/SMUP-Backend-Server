package com.appsolute.soomapi.domain.chat.data.response

import com.appsolute.soomapi.domain.chat.data.type.MessageType
import java.time.LocalDateTime

class HttpMessageResponse (
    messageId: String,
    sender: Sender?,
    content: String,
    sentAt: LocalDateTime,
    chatRoomId: String,
    messageType: MessageType,
    isMine: Boolean,
    isDeleted: Boolean

): MessageResponse(
    messageId,
    sender,
    content,
    sentAt,
    chatRoomId,
    messageType
){
    val isMine: Boolean = isMine

    val isDeleted: Boolean = isDeleted

}

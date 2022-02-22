package com.appsolute.soomapi.domain.chat.data.request

import com.appsolute.soomapi.domain.chat.data.type.ContentType
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.soom.data.type.FileType

data class SendMessageRequest (
    val message: String,
    val chatRoomId: String,
    val contentType: ContentType
)

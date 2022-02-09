package com.appsolute.soomapi.domain.chat.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash


@RedisHash
class MessageCount(
    userId: String,
    chatRoomId: String,
    messageCount: Int

) {
    @Id
    val id: String =  userId + chatRoomId + "messageCount"

    var messageCount = messageCount

}
package com.appsolute.soomapi.domain.soom.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 51840000)
class RejectedUser(
    groupId: String,
    userId: String
) {
    @Id
    var id = groupId + userId
    @Indexed
    var groupId: String = groupId

}
package com.appsolute.soomapi.domain.soom.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 604800) // 7일
class JoinRejectedUser(
    groupId: String,
    userId: String
) {
    @Id
    var id = groupId + userId + "rejected"
    @Indexed
    var groupId: String = groupId

}
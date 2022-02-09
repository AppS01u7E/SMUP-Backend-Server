package com.appsolute.soomapi.domain.alarm.data.entity

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.alarm.data.response.ToMeAlarmResponse
import org.springframework.data.annotation.Id

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*


@RedisHash(timeToLive = 1209600000)
// 알람은 14일 이내의 기록만 보관합니다
class Alarm(
    title: String,
    message: String,
    sender: User,
    receiver: User
) {

    @Id
    private val id: String = UUID.randomUUID().toString()

    private val title: String = title

    private val message: String = message
    @Indexed
    private val sender: User = sender
    @Indexed
    private val receiver: User = receiver

    private var isRead: Boolean = false

    fun toToMeAlarmResponse(): ToMeAlarmResponse{
        return ToMeAlarmResponse(
            this.id,
            this.title,
            this.message,
            this.sender.toUserResponse()
        )
    }

    fun makeRead() {
        this.isRead = true
    }

}
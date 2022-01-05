package com.appsolulte.smupbackendserver.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.ElementCollection
import javax.persistence.Embeddable

@Embeddable
@RedisHash
class DeviceToken(
    memberId: String
) {
    @Id
    private val id: String = memberId
    @Indexed
    @ElementCollection
    private var tokenList: MutableList<String> = ArrayList<String>()
    @TimeToLive
    private var expiration: Long? = null

    fun deleteToken(token: String){
        this.tokenList.remove(token)
    }

    fun addToken(token: String) {
        this.tokenList.add(token)
    }

    fun getToken(): List<String> {
        return this.tokenList.toList()
    }


}
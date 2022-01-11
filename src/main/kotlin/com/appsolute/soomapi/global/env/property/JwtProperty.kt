package com.appsolute.soomapi.global.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("jwt")
data class JwtProperty (
    val secret: String,
    val accessExpiredAt: Long,
    val refreshExpiredAt: Long
)

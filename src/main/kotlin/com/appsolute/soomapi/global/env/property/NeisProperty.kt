package com.appsolute.soomapi.global.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("neis")
data class NeisProperty(
    val secretKey: String
)
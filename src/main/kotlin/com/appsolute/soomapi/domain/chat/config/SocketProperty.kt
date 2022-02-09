package com.appsolute.soomapi.domain.chat.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("socket")
data class SocketProperty (
    val port: Int

)

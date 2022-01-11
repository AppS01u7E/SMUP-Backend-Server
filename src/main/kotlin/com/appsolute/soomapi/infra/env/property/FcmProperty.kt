package com.appsolute.soomapi.infra.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import java.beans.ConstructorProperties

@ConstructorBinding
@ConfigurationProperties("fcm")
data class FcmProperty (
    val url: String,
    val configPath: String
)
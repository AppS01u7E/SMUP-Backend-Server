package com.appsolute.soomapi.infra.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("fcm")
data class FcmProperty (
    private val url: String,
    private val configPath: String
)
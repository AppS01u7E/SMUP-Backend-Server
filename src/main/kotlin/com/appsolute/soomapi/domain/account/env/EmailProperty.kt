package com.appsolute.soomapi.domain.account.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("email")
data class EmailProperty (
    val prefix: String
)

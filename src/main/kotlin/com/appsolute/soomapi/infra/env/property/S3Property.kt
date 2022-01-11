package com.appsolute.soomapi.infra.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("s3")
data class S3Property (
    val accessKey: String,
    val secretKey: String,
    val region: String,
    val bucket: String,
    val expired: Long
)


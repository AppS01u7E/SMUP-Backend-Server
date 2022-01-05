package com.appsolulte.smupbackendserver.global.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "defined")
class GlobalProperties (
    val fcm: FcmProperties,
    val email: EmailProperties,
    val security: SecurityProperties
){
    data class FcmProperties(
        var url: String,
        var firebaseConfigPath: String
    )
    data class EmailProperties(
        var account: String,
        var password: String
    )
    data class SecurityProperties(
        val secretKey: String,
        val accessExpire: Int,
        val refreshExpire: Int
    )




}
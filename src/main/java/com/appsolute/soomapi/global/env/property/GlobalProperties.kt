package com.appsolute.soomapi.global.env.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "defined")
@kotlin.Deprecated(message = "fcm 은 따로 Property 만들어주시고 Email 과 Security 는 JwtUtils 와 SMTP mailsenderservice 로 대체 가능할 듯 합니다", level = DeprecationLevel.ERROR)
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
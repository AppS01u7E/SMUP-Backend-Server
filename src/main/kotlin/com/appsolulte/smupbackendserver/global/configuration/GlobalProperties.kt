package com.appsolulte.smupbackendserver.global.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "defined")
class GlobalProperties (
    val fcm: FcmProperties
){
    data class FcmProperties(
        var url: String,
        var firebaseConfigPath: String
    )




}
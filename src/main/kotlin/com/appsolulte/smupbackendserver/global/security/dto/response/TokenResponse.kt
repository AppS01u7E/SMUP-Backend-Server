package com.appsolulte.smupbackendserver.global.security.dto.response

data class TokenResponse(
    var accessToken: String,
    var refreshToken: String
){

//    fun TokenResponse(accessToken: String, refreshToken: String){
//        this.accessToken = accessToken
//        this.refreshToken = refreshToken
//    }
}

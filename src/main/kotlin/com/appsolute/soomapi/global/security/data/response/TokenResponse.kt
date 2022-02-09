package com.appsolute.soomapi.global.security.data.response

data class TokenResponse(
    var accessToken: String,
    var refreshToken: String
)

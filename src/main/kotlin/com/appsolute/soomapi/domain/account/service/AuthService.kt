package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.request.*
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import com.google.api.client.auth.oauth2.RefreshTokenRequest


interface AuthService {

    fun login(request: LoginRequest): TokenResponse
    fun studentSignup(request: StudentSignupRequest): TokenResponse
    fun teacherSignup(request: TeacherSignupRequest): TokenResponse

    fun reissue(request: RefreshTokenReissueRequest): TokenResponse


}
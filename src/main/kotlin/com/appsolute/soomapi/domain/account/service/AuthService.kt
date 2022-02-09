package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.request.LoginRequest
import com.appsolute.soomapi.domain.account.data.dto.request.SignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.StudentSignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.TeacherSignupRequest
import com.appsolute.soomapi.global.security.data.response.TokenResponse


interface AuthService {

    fun login(request: LoginRequest): TokenResponse
    fun signup(request: SignupRequest): () -> TokenResponse


}
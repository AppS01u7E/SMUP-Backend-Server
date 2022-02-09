package com.appsolute.soomapi.domain.account.controller

import com.appsolute.soomapi.domain.account.data.dto.request.LoginRequest
import com.appsolute.soomapi.domain.account.data.dto.request.SignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.StudentSignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.TeacherSignupRequest
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.service.AuthService
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import kotlin.reflect.cast


@RestController
@RequestMapping("/api/v1/account/auth")
class AuthController(
    private val authService: AuthService
) {


    @PostMapping("/login")
    fun login(@Valid request: LoginRequest): TokenResponse {
        return authService.login(request)
    }


    @PostMapping("/signup")
    fun signup(@Valid request: SignupRequest): () -> TokenResponse {
        return authService.signup(request)
    }



}
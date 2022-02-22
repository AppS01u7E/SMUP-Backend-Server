package com.appsolute.soomapi.domain.account.controller

import com.appsolute.soomapi.domain.account.data.dto.request.*
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.service.AuthService
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import com.google.api.client.auth.oauth2.RefreshTokenRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    fun login(@RequestBody@Valid request: LoginRequest): TokenResponse {
        return authService.login(request)
    }


    @PostMapping("/signup")
    fun signup(@RequestBody@Valid request: StudentSignupRequest): TokenResponse {
        return authService.studentSignup(request)
    }

    @PostMapping("/signup/teacher")
    fun teacherSignup(@RequestBody@Valid request: TeacherSignupRequest): TokenResponse{
        return authService.teacherSignup(request)
    }

    @PostMapping("/refresh")
    fun reissue(@RequestBody request: RefreshTokenReissueRequest): TokenResponse{
        return authService.reissue(request)
    }


}
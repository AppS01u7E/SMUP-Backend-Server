package com.appsolute.soomapi.domain.account.data.dto.request

import com.appsolute.soomapi.domain.account.data.dto.request.type.TokenType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequest (
    @Email(message = "유효하지 않은 email입니다.")
    val email: String,
    @NotBlank
    val password: String,
    @NotBlank(message = "deviceToken이 비어서는 안됩니다.")
    val deviceToken: String,
    @NotBlank(message = "현재 로그인한 기기의 type을 선택해주십시오.")
    val tokenType: TokenType
)
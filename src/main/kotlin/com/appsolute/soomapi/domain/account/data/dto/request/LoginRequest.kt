package com.appsolute.soomapi.domain.account.data.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequest (
    @Email(message = "유효하지 않은 email입니다.")
    val email: String,
    @NotBlank
    val password: String,
    @NotBlank(message = "deviceToken이 비어서는 안됩니다.")
    val deviceToken: String
)
package com.appsolulte.smupbackendserver.domain.account.dto.response

import com.appsolute.soomapi.domain.account.data.entity.user.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.Role

data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val type: Role
)
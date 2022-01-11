package com.appsolute.soomapi.domain.account.data.dto.response

import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role

data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val type: Role
)
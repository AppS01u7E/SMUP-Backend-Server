package com.appsolute.soomapi.domain.account.data.deprecated

import com.appsolute.soomapi.domain.account.data.type.Gender
import com.appsolute.soomapi.domain.account.data.type.Role

@kotlin.Deprecated("해당 클래스 어떤의미인가요?? TeacherSign", level = DeprecationLevel.ERROR)
data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val type: Role
)
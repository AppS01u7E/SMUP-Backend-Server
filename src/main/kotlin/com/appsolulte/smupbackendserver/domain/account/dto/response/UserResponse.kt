package com.appsolulte.smupbackendserver.domain.account.dto.response

import com.appsolulte.smupbackendserver.domain.account.entity.Dept
import com.appsolulte.smupbackendserver.domain.account.entity.Gender
import com.appsolulte.smupbackendserver.domain.account.entity.Role
import com.appsolulte.smupbackendserver.domain.account.entity.TeacherType

data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val type: Role
)
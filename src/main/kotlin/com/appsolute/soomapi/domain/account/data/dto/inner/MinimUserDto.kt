package com.appsolute.soomapi.domain.account.data.dto.inner

import com.appsolute.soomapi.domain.account.data.entity.user.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.Role
import com.appsolute.soomapi.global.school.data.type.SchoolType

data class MinimUserDto (
    var id: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var birth: String,
    var schoolType: SchoolType,
    var userType: Role
)
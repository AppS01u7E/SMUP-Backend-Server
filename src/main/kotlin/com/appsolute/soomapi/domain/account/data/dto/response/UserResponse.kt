package com.appsolute.soomapi.domain.account.data.dto.response

import com.appsolute.soomapi.domain.account.data.dto.response.type.UserType
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.global.school.data.type.SchoolType
import java.time.LocalDateTime

class UserResponse(
    id: String,
    profilePhoto: String?,
    school: SchoolType,
    birth: String,
    createdAt: LocalDateTime,
    email: String,
    name: String,
    gender: Gender
): ProfileResponse(
    id,
    profilePhoto,
    school,
    birth,
    createdAt,
    email,
    name,
    gender

){

}
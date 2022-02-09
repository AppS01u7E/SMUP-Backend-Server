package com.appsolute.soomapi.domain.account.data.dto.response

import com.appsolute.soomapi.domain.account.data.dto.response.type.UserType
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.global.school.data.type.SchoolType
import java.time.LocalDateTime

abstract class ProfileResponse (
    id: String,
    profilePhoto: String?,
    school: SchoolType,
    birth: String,
    createdAt: LocalDateTime,
    email: String,
    name: String,
    gender: Gender

){
    val id: String = id
    val profilePhoto: String? = profilePhoto
    val school: SchoolType = school
    val birth: String = birth
    val createdAt: LocalDateTime = createdAt
    val email: String = email
    val name: String = name
    val gender: Gender = gender
}


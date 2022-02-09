package com.appsolute.soomapi.domain.account.data.dto.response

import com.appsolute.soomapi.domain.account.data.dto.response.type.UserType
import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.global.school.data.type.SchoolType
import java.time.LocalDateTime

class StudentResponse(
    id: String,
    profilePhoto: String?,
    school: SchoolType,
    birth: String,
    createdAt: LocalDateTime,
    email: String,
    name: String,
    gender: Gender,
    classNum: Int,
    number: Int,
    ent: Int,
    dept: Dept
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

    val classNum: Int = classNum
    val number: Int = number
    val enterance: Int = ent
    val dept: Dept = dept
    val userType: UserType = UserType.STUDENT

}
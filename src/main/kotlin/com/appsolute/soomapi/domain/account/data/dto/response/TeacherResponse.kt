package com.appsolute.soomapi.domain.account.data.dto.response

import com.appsolute.soomapi.domain.account.data.dto.response.type.UserType
import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.TeacherType
import com.appsolute.soomapi.global.school.data.type.SchoolType
import java.time.LocalDateTime

class TeacherResponse(
    id: String,
    profilePhoto: String?,
    school: SchoolType,
    birth: String,
    createdAt: LocalDateTime,
    email: String,
    name: String,
    gender: Gender,
    major: String,
    teacherType: TeacherType
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
    val major: String = major
    val teacherType: TeacherType = teacherType
    val userType: UserType = UserType.TEACHER

}
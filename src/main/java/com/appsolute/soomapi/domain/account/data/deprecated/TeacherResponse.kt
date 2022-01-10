package com.appsolute.soomapi.domain.account.data.deprecated

import com.appsolute.soomapi.domain.account.data.type.Gender
import com.appsolute.soomapi.domain.account.data.type.TeacherType


@kotlin.Deprecated("해당 클래스 어떤의미인가요?? TeacherSign", level = DeprecationLevel.ERROR)
data class TeacherResponse (
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val teacherType: TeacherType,
    val major: String

)
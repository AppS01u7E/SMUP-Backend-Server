package com.appsolute.soomapi.domain.account.data.deprecated

import com.appsolute.soomapi.domain.account.data.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.Teacher
import com.appsolute.soomapi.domain.account.data.type.TeacherType

@kotlin.Deprecated("domain.account.data.request.TeacherSignupRequest 를 사용해주세요! [지인호]", level = DeprecationLevel.ERROR)
data class TeacherSignupRequest(
    val email: String,
    val firstName: String,
    val lastname: String,
    val gender: Gender,
    val birth: String,
    val password: String,
    val teacherType: TeacherType,
    val major: String

) {
    fun toTeacher(id: String, password: String): Teacher{
        return Teacher(id, this.email, this.firstName, this.lastname, this.gender, this.birth, password, this.teacherType, this.major)

    }
}

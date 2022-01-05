package com.appsolulte.smupbackendserver.domain.account.dto.request

import com.appsolulte.smupbackendserver.domain.account.entity.Gender
import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import com.appsolulte.smupbackendserver.domain.account.entity.TeacherType
import net.bytebuddy.utility.RandomString

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

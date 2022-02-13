package com.appsolute.soomapi.domain.account.data.dto.request

import com.appsolute.soomapi.domain.account.data.dto.request.SignupRequest
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.type.TeacherType
import com.appsolute.soomapi.global.school.data.type.SchoolType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class TeacherSignupRequest(
    emailToken: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    @Pattern(regexp = "/^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$/",
        message = "유효하지 않은 생년월일입니다.")
    birth: String,
    password: String,
    school: SchoolType,
    @NotBlank(message = "teacherType은 공백일 수 없습니다.")
    teacherType: TeacherType,
    @NotBlank(message = "major는 공백일 수 없습니다.")
    major: String,
    @NotBlank(message = "teacher Code는 공회백일 수 없습니다.")
    teacherCode: String

): SignupRequest(
    emailToken,
    firstName,
    lastName,
    gender,
    birth,
    password,
    school
){
    val teacherType: TeacherType = teacherType

    val major: String = major
    val teacherCode = teacherCode

    fun toTeacher(id: String, email: String, encodedPassword: String): Teacher{
        return Teacher(
            id,
            email,
            firstName,
            lastName,
            gender,
            birth,
            encodedPassword,
            teacherType,
            major,
            school
        )
    }

}

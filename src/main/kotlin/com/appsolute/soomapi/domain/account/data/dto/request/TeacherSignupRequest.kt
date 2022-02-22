package com.appsolute.soomapi.domain.account.data.dto.request

import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.type.TeacherType
import com.appsolute.soomapi.global.school.data.type.SchoolType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class TeacherSignupRequest(
    @NotBlank(message = "email Token은 공백일 수 없습니다.")
    emailToken: String,
    @NotBlank(message = "firstName은 공백일 수 없습니다.")
    firstName: String,
    @NotBlank(message = "lastName은 공백일 수 없습니다.")
    lastName: String,
    @NotBlank(message = "성별은 공백일 수 없습니니다.") //MAN, WOMAN
    gender: Gender,
    @Pattern(regexp = "/^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$/",
        message = "유효하지 않은 생년월일입니다.")
    birth: String,
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    password: String,
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
    password
){
    val teacherType: TeacherType = teacherType

    val major: String = major
    val teacherCode = teacherCode

    fun toTeacher(id: String, email: String, encodedPassword: String, school: SchoolType): Teacher{
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

package com.appsolute.soomapi.domain.account.data.dto.request

import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.global.school.data.type.SchoolType
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.RegEx
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern


class StudentSignupRequest(
    emailToken: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    @Pattern(regexp = "/^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$/",
        message = "유효하지 않은 생년월일입니다.")
    birth: String, //ex. 20050817
    password: String,
    school: SchoolType,
    @NotBlank(message = "학과는 공백일 수 없습니다.")
    dept: Dept,
    @NotBlank(message = "grade는 공백일 수 없습니다.")
    @Max(3, message = "너무 높은 값입니다.")
    @Min(0, message = "grade는 음수일 수 없습니다.")
    grade: Int,
    @NotBlank(message = "classNum은 공백일 수 없습니다.")
    @Max(3, message = "너무 높은 값입니다.")
    @Min(0, message = "classNum은 음수일 수 없습니다.")
    classNum: Int,
    @NotBlank(message = "번호는 공백일 수 없습니다.")
    @Max(30, message = "번호 입력값이 너무 높습니다.")
    @Min(0, message = "번호는 음수일 수 없습니다.")
    number: Int,
    @NotBlank(message = "입학년도가 올바르지 않습니다.")
    @Min(2000, message = "입학년도가 올바르지 않습니다.")
    @Max(3000, message = "입학년도가 올바르지 않습니다.")
    ent: Int

): SignupRequest(
    emailToken,
    firstName,
    lastName,
    gender,
    birth,
    password,
    school,
){
    val dept = dept
    val grade = grade
    val classNum = classNum
    val number = number
    val ent = ent

    fun toStudent(randomId: String, email: String, encodedPassword: String): Student{
        return Student(
            randomId,
            email,
            firstName,
            lastName,
            gender,
            birth,
            encodedPassword,
            dept,
            grade,
            classNum,
            number,
            ent,
            school
        )

    }

}

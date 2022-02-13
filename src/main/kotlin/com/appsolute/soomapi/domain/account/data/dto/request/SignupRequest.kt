package com.appsolute.soomapi.domain.account.data.dto.request

import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.global.school.data.type.SchoolType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

abstract class SignupRequest(
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
    @NotBlank(message = "재학중인 학교를 선택해주십시오.") // BUSAN, DAEDEOK, DAEGU, GWANGJU
    school: SchoolType,

){
    val emailToken: String = emailToken
    val firstName: String = firstName
    val lastName: String = lastName
    val gender: Gender = gender
    val birth: String = birth
    val password: String = password
    val school: SchoolType = school
}
package com.appsolulte.smupbackendserver.domain.account.entity

import com.appsolulte.smupbackendserver.domain.account.dto.response.TeacherResponse
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("TEACHER")
class Teacher(
    id: String,
    email: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    birth: String,
    password: String,
    teacherType: TeacherType,
    major: String
) : User(
    id,
    email,
    firstName,
    lastName,
    gender,
    birth,
    password,
    Role.TEACHER
) {
    private var teacherType = teacherType

    private var major = major


    fun toTeacherResponse(): TeacherResponse{
        return TeacherResponse(
            this.id,
            this.getEmail(),
            this.getFirstName(),
            this.getLastName(),
            this.getGender(),
            this.teacherType,
            this.major
        )
    }

}
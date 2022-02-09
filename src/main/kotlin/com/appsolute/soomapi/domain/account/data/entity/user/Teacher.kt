package com.appsolute.soomapi.domain.account.data.entity.user

import com.appsolute.soomapi.domain.account.data.dto.response.TeacherResponse
import com.appsolute.soomapi.domain.account.data.dto.inner.MinimTeacherDto
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.account.data.entity.user.type.TeacherType
import com.appsolute.soomapi.global.school.data.type.SchoolType
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
    major: String,
    school: SchoolType
) : User(
    id,
    email,
    firstName,
    lastName,
    gender,
    birth,
    password,
    Role.TEACHER,
    school
) {
    private var teacherType = teacherType

    private var major = major


    fun toTeacherResponse(): TeacherResponse {
        return TeacherResponse(
            this.id,
            this.profile,
            this.school,
            this.getBirth(),
            this.createdAt,
            this.getEmail(),
            this.getLastName() + this.getFirstName(),
            this.getGender(),
            this.major,
            this.teacherType

        )
    }

    fun toMinimTeacherDto(): MinimTeacherDto{
        return MinimTeacherDto(
            this.toMinimUserDto(),
            this.teacherType,
            this.major
        )
    }

}
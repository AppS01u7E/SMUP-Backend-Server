package com.appsolute.soomapi.domain.account.data.entity.user

import com.appsolute.soomapi.domain.account.data.dto.inner.MinimStudentDto
import com.appsolute.soomapi.domain.account.data.dto.response.StudentResponse
import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.global.school.data.type.SchoolType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated


@Entity
@DiscriminatorValue("STUDENT")
class Student(
    id: String,
    email: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    birth: String,
    password: String,
    dept: Dept,
    grade: Int,
    classNum: Int,
    number: Int,
    ent: Int,
    school: SchoolType
) : User(
    id,
    email,
    firstName,
    lastName,
    gender,
    birth,
    password,
    Role.STUDENT,
    school
) {
    @Enumerated(EnumType.STRING)
    private var dept: Dept = dept

    private var grade = grade

    private var classNum = classNum

    private var number = number

    private var ent = ent

    fun toMinimStudentDto(): MinimStudentDto{
        return MinimStudentDto(
            this.toMinimUserDto(),
            this.dept,
            this.grade,
            this.classNum,
            this.number,
            this.ent
        )
    }

    fun getGrade(): Int{
        return this.grade
    }

    fun getClass(): Int{
        return this.classNum
    }


    fun toStudentResponse(): StudentResponse{
        return StudentResponse(
            this.uuid,
            this.profile,
            this.school,
            this.getBirth(),
            this.createdAt,
            this.getEmail(),
            this.getLastName() + this.getFirstName(),
            this.getGender(),
            this.classNum,
            this.number,
            this.ent,
            this.dept
        )
    }

}
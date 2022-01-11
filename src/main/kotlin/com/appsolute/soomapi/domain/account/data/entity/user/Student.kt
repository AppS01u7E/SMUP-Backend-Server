package com.appsolute.soomapi.domain.account.data.entity.user

import com.appsolute.soomapi.domain.account.data.dto.inner.MinimStudentDto
import com.appsolute.soomapi.global.school.data.type.SchoolType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


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

    private var dept = dept

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

}
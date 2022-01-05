package com.appsolulte.smupbackendserver.domain.account.entity

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
    ent: Int
) : User(
    id,
    email,
    firstName,
    lastName,
    gender,
    birth,
    password

) {

    private var dept = dept

    private var grade = grade

    private var classNum = classNum

    private var number = number

    private var ent = ent


}
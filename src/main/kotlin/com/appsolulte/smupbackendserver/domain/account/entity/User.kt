package com.appsolulte.smupbackendserver.domain.account.entity

import javax.persistence.*


@Entity
@DiscriminatorColumn(name = "UTYPE")
abstract class User (
    id: String,
    email: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    birth: String,
    password: String

        ){
    @Id
    private val id: String = id

    private val email: String = email

    private var firstName = firstName

    private var lastName = lastName

    private var gender = gender

    private var birth = birth

    private var password = password
    




}
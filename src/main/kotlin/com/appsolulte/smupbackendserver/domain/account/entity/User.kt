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
    password: String,
    role: Role
        ){
    @Id
    val id: String = id

    private val email: String = email

    private var firstName = firstName

    private var lastName = lastName

    private var gender = gender

    private var birth = birth

     var password = password

    private var role = role

    fun getEmail(): String{
        return this.email
    }

    fun getRole(): Role{
        return this.role
    }

}
package com.appsolute.soomapi.domain.account.data.entity.user

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.entity.Post
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import com.appsolute.soomapi.global.school.data.type.SchoolType
import java.time.LocalDateTime
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
    role: Role,
    school: SchoolType
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

    var profile: String? = null

    var school: SchoolType = school
    @OneToMany(fetch = FetchType.LAZY)
    var groupInfo: MutableList<GroupInfo> = ArrayList<GroupInfo> ()

    var createdAt = LocalDateTime.now()

    @OneToMany(fetch = FetchType.LAZY)
    var post: MutableList<Post> = ArrayList<Post>()


    fun getEmail(): String{
        return this.email
    }

    fun getRole(): Role {
        return this.role
    }

    fun getFirstName(): String{
        return this.firstName
    }

    fun getLastName(): String{
        return this.lastName
    }

    fun getGender(): Gender {
        return this.gender
    }

    fun joinGroup(group: Group): User {
        this.groupInfo.add(GroupInfo(this, group, GroupAuthType.MEMBER))
        return this
    }


    fun settingProfile(profile: String){
        this.profile = profile
    }

    fun toUserResponse(): UserResponse{
        return UserResponse(
            this.id,
            this.email,
            this.firstName,
            this.lastName,
            this.gender,
            this.role
        )
    }

}
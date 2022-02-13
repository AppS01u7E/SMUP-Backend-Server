package com.appsolute.soomapi.domain.account.data.entity.user

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.data.dto.inner.MinimUserDto
import com.appsolute.soomapi.domain.account.data.dto.response.ShortnessUserResponse
import com.appsolute.soomapi.domain.account.data.entity.user.type.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.type.Role
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.entity.Post
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import com.appsolute.soomapi.global.error.exception.AlreadyAcceptedRequestException
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
    val uuid: String = id

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

    var discord: String? = null

    @ManyToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val chatRoomList: MutableList<ChatRoom> = ArrayList<ChatRoom>()

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

    fun joinGroup(soom: Soom): User {
        var groupAuth = ArrayList<GroupAuthType>()
        groupAuth.add(GroupAuthType.NONE)
        this.groupInfo.add(GroupInfo(this, soom, groupAuth))
        return this
    }

    fun getOutGroup(groupInfo: GroupInfo): User{
        this.groupInfo.remove(groupInfo)
        groupInfo.group.chattingRoom.kickMember(this)
        return this
    }

    fun getChatRoomList(): List<ChatRoom>{
        return this.chatRoomList
    }

    fun settingProfile(profile: String?){
        this.profile = profile
    }

    fun toUserResponse(): UserResponse {
        return UserResponse(
            this.uuid,
            this.profile,
            this.school,
            this.birth,
            this.createdAt,
            this.email,
            this.lastName+this.firstName,
            this.gender
        )
    }

    fun toShortnessUserResponse(): ShortnessUserResponse{
        return ShortnessUserResponse(
            this.uuid,
            this.lastName + this.firstName,
            this.profile
        )
    }


    fun toMinimUserDto(): MinimUserDto{
        return MinimUserDto(
            this.uuid,
            this.email,
            this.firstName,
            this.lastName,
            this.gender,
            this.birth,
            this.school,
            this.role
        )
    }

    fun getName(): String{
        return this.lastName + this.firstName
    }

    fun getBirth(): String{
        return this.birth
    }

    fun deleteGroupRequest(group: Soom): Soom{
        if (!group.deleteVoterList.contains(this)) {
            group.deleteVoterList.add(this)
        } else throw AlreadyAcceptedRequestException("already voted deletion")
        return group
    }

    fun cancelDeleteGroupRequest(group: Soom): Soom{
        if (group.deleteVoterList.contains(this)) {
            group.deleteVoterList.remove(this)
        } else throw AlreadyAcceptedRequestException("Don't vote for deletion")
        return group
    }


}
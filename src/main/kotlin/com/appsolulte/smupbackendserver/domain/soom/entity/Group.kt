package com.appsolulte.smupbackendserver.domain.soom.entity

import com.appsolulte.smupbackendserver.domain.account.entity.Student
import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.chatting.entity.ChattingRoom
import com.appsolulte.smupbackendserver.domain.chatting.entity.ChattingRoomType
import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupResponse
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


@Entity
class Group(
    id: String,
    name: String,
    description: String,
    type: GroupType,
    header: User
) {
    @Id
    var id: String = id
    var name = name
    var description = description
    var type: GroupType = type
    @ManyToOne
    var header: User = header
    @OneToMany
    var subHeaderList: MutableList<User> = ArrayList<User>()
    @OneToMany
    var memberList: MutableList<User> = ArrayList<User>()
    var profile: String? = null

    @ElementCollection
    var subHeaderAuthPolicy: MutableList<GroupAuthPolicy> = ArrayList<GroupAuthPolicy>()
    @ManyToOne
    var teacher: Teacher? = null


    @OneToOne
    var chattingRoom: ChattingRoom = ChattingRoom(UUID.randomUUID().toString(), name, ChattingRoomType.SOOM)
        .setMembers(memberList)

    fun settingProfile(imageUrl: String){
        this.profile = imageUrl
    }

    fun settingTeacher(teacher: Teacher){
        this.teacher = teacher
    }

    fun toGroupResponse(): GroupResponse{
        return GroupResponse(
            this.id,
            this.name,
            this.description,
            this.type,
            this.header,
            this.subHeaderList,
            this.profile,
            this.memberList.size,
            this.memberList,
            this.teacher?.toTeacherResponse()
        )

    }




}
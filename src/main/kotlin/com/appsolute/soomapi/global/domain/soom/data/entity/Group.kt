package com.appsolute.soomapi.global.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.Student
import com.appsolute.soomapi.domain.account.data.entity.Teacher
import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.chatting.data.entity.ChattingRoom
import com.appsolute.soomapi.domain.chatting.data.type.ChattingRoomType
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthPolicyType
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.repository.GroupInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList
import kotlin.jvm.Transient
import kotlin.streams.toList


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
    @ManyToOne(fetch = FetchType.LAZY)
    var header: User = header
    @OneToMany(fetch = FetchType.LAZY)
    var subHeaderList: MutableList<User> = ArrayList<User>()
    @ManyToMany(fetch = FetchType.LAZY)
    var memberList: MutableList<User> = ArrayList<User>()
    var profile: String? = null

    @ElementCollection
    var subHeaderAuthPolicy: MutableList<GroupAuthPolicyType> = ArrayList<GroupAuthPolicyType>()
    @ManyToOne(fetch = FetchType.LAZY)
    var teacher: Teacher? = null

    @OneToMany(fetch = FetchType.LAZY)
    var deleteVoterList: MutableList<User> = ArrayList<User>()

    @OneToOne(fetch = FetchType.LAZY)
    var chattingRoom: ChattingRoom = ChattingRoom(UUID.randomUUID().toString(), name, ChattingRoomType.SOOM)
        .setMembers(memberList)

    @OneToMany(fetch = FetchType.LAZY)
    var joinRequestMemberList: MutableList<Student> = ArrayList<Student>()

    @OneToMany(fetch = FetchType.LAZY)
    var postList: MutableList<Post> = ArrayList<Post>()


    @Autowired
    @Transient
    private lateinit var groupInfoRepository: GroupInfoRepository



    fun setSubHeader(member: User): Group{
        this.subHeaderList.add(member)
        return this
    }

    fun dismissalSubHeader(member: User): Group{
        this.subHeaderList.remove(member)
        return this
    }

    fun addJoinRequestMemberList(student: Student){
        this.joinRequestMemberList.add(student)
    }

    fun cancelJoinRequest(student: Student){
        this.joinRequestMemberList.remove(student)

    }

    fun approveJoinRequest(student: Student){
        this.joinRequestMemberList.remove(student)
        this.memberList.add(student)
        student.joinGroup(this)
    }

    fun addMemberList(user: User){
        this.memberList.add(user)
    }

    fun removeMember(user: User){
        this.memberList.remove(user)
    }

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
            this.subHeaderList.stream().map { it.toUserResponse() }.toList(),
            this.profile,
            this.memberList.size,
            this.memberList.stream().map { it.toUserResponse() }.toList(),
            this.teacher?.toTeacherResponse()
        )

    }

    fun editGroup(r: EditGroupRequest): Group{
        this.name = r.name
        this.description = r.description
        return this
    }

    fun setType(type: GroupType): Group{
        this.type = type
        return this
    }

    fun deleteVote(member: User): Group{
        if (deleteVoterList.contains(member)) {
            this.deleteVoterList.remove(member)
        } else{
            this.deleteVoterList.add(member)
        }

        return this
    }

    fun kickMember(member: User, groupInfo: GroupInfo): Group{
        this.memberList.remove(member)
        groupInfoRepository.delete(groupInfo)
        return this
    }


}
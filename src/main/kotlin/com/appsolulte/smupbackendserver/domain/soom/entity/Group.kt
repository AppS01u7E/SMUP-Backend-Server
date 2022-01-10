package com.appsolulte.smupbackendserver.domain.soom.entity

import com.appsolulte.smupbackendserver.domain.account.entity.GroupInfo
import com.appsolulte.smupbackendserver.domain.account.entity.Student
import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.chatting.entity.ChattingRoom
import com.appsolulte.smupbackendserver.domain.chatting.entity.ChattingRoomType
import com.appsolulte.smupbackendserver.domain.soom.dto.request.EditGroupRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupResponse
import com.appsolulte.smupbackendserver.domain.soom.repository.GroupInfoRepository
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
    var subHeaderAuthPolicy: MutableList<GroupAuthPolicy> = ArrayList<GroupAuthPolicy>()
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
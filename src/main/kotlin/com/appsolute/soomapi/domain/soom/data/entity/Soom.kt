package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.chat.data.type.ChatRoomType
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthPolicyType
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.repository.group.GroupInfoRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.*
import kotlin.collections.ArrayList
import kotlin.jvm.Transient


@Entity
class Soom(
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
    var profileBanner: String? = null

    @ElementCollection
    var subHeaderAuthPolicy: MutableList<GroupAuthPolicyType> = ArrayList<GroupAuthPolicyType>()
    @ManyToOne(fetch = FetchType.LAZY)
    var teacher: Teacher? = null

    @OneToMany(fetch = FetchType.LAZY)
    var deleteVoterList: MutableList<User> = ArrayList<User>()

    @OneToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE))
    var chattingRoom: ChatRoom = ChatRoom(this.id, name, ChatRoomType.GROUP, this.profile, this.memberList, this.header, this)

    @OneToMany(fetch = FetchType.LAZY)
    var joinRequestMemberList: MutableList<Student> = ArrayList<Student>()

    @OneToMany(fetch = FetchType.LAZY)
    var postList: MutableList<Post> = ArrayList<Post>()



    fun setSubHeader(member: User): Soom{
        this.subHeaderList.add(member)
        return this
    }

    fun dismissalSubHeader(member: User): Soom{
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

    fun isMember(user: User): Boolean{
        if (this.memberList.contains(user)) return true
        return false
    }


    fun settingProfile(image: String){
        this.profile = image
    }
    fun settingBannerProfile(image: String){
        this.profileBanner = image
    }

    fun settingTeacher(teacher: Teacher){
        this.teacher = teacher
    }

    fun toGroupResponse(user: User): GroupResponse{
        return GroupResponse(
            this.id,
            this.name,
            this.description,
            this.type,
            this.header,
            this.subHeaderList.stream().map { it.toUserResponse() }.toList(),
            this.profile,
            this.profileBanner,
            this.memberList.size,
            this.memberList.stream().map { it.toUserResponse() }.toList(),
            this.teacher?.toTeacherResponse(),
            this.memberList.contains(user),
            this.joinRequestMemberList.contains(user)
        )

    }

    fun toShortnessGroupResponse(): ShortnessGroupResponse{
        return ShortnessGroupResponse(
            this.id,
            this.name,
            this.header.toShortnessUserResponse(),
            this.memberList.size
        )
    }

    fun editGroup(r: EditGroupRequest): Soom{
        this.name = r.name
        this.description = r.description
        return this
    }

    fun setType(type: GroupType): Soom{
        this.type = type
        return this
    }

    fun kickMember(member: User, groupInfo: GroupInfo): Soom{
        this.memberList.remove(member)
        member.getOutGroup(groupInfo)
        return this
    }

    fun acceptAllJoinRequest() {
        this.memberList.addAll(this.joinRequestMemberList)
        this.joinRequestMemberList.removeAll(this.joinRequestMemberList)
    }

    fun rejectAllJoinRequest() {
        this.joinRequestMemberList.removeAll(this.joinRequestMemberList)
    }

}
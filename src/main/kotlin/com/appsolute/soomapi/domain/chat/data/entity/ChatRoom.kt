package com.appsolute.soomapi.domain.chat.data.entity

import com.appsolute.soomapi.domain.chat.data.request.SendMessageRequest
import com.appsolute.soomapi.domain.chat.data.type.ChatRoomType
import com.appsolute.soomapi.domain.chat.data.type.InterviewResultType
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.chat.service.ChatService
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.response.ChatRoomResponse
import com.appsolute.soomapi.domain.soom.data.entity.File
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.corundumstudio.socketio.SocketIOClient
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
class ChatRoom(
    id: String,
    name: String,
    type: ChatRoomType,
    profile: String?,
    memberList: MutableList<User>,
    admin: User,
    group: Soom?

) {

    @Id
    val id: String = id

    private val name: String = name

    private val type: ChatRoomType = type

    private val profile: String? = profile
    @ManyToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val memberList = memberList
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val admin: User = admin
    @OneToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val message: MutableList<Message> = ArrayList<Message>()
    @OneToOne
    val group: Soom? = group

    val chatCount: Int = 0

    private val createdAt: LocalDateTime = LocalDateTime.now()

    private var isDone: Boolean = false

    private var isAccept: Boolean = false

    @OneToMany
    var alarmReceiverList: MutableList<User> = memberList

    @ElementCollection
    private val file: MutableList<File>? = null

    @Transient
    private lateinit var chatService: ChatService


    fun getFile(): List<File>? {
        return this.file
    }

    fun changeAlarmListenStatus(current: User): Boolean{
        if (alarmReceiverList.contains(current)) this.alarmReceiverList.remove(current)
        else this.alarmReceiverList.add(current)
        return this.alarmReceiverList.contains(current)
    }

    fun getName(): String{
        return this.name
    }


    fun isMember(user: User): Boolean{
        if (!this.memberList.contains(user)) return false
        return true
    }

    fun checkIsDone(): ChatRoom?{
        if (this.isDone) return null
        return this
    }

    fun makeDone(){
        this.isDone = true
    }

    fun kickMember(user: User){
        if (this.memberList.contains(user)) this.memberList.remove(user)
    }


    fun toChatRoomResponse(messageCount: MessageCount): ChatRoomResponse{
        return ChatRoomResponse(
            this.id,
            this.name,
            this.type,
            this.profile.toString(),
            this.memberList.size,
            this.admin.toShortnessUserResponse(),
            this.group?.toShortnessGroupResponse(),
            this.chatCount - messageCount.messageCount,
            this.isDone
        )
    }

}
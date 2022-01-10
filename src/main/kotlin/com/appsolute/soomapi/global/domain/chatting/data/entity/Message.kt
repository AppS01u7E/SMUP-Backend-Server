package com.appsolute.soomapi.global.domain.chatting.data.entity

import com.appsolute.soomapi.domain.chatting.data.type.MessageType
import com.appsolute.soomapi.domain.account.data.entity.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Message(
    content: String,
    sender: User,
    chattignRoom: ChattingRoom,
    type: MessageType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var content: String = content

    @ManyToOne
    var sender: User = sender
    @ManyToOne
    var chattingRoom: ChattingRoom = chattignRoom

    var type: MessageType = type

    var sentAt: LocalDateTime = LocalDateTime.now()





}
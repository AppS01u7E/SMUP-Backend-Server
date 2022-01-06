package com.appsolulte.smupbackendserver.domain.chatting.entity

import com.appsolulte.smupbackendserver.domain.account.entity.User
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
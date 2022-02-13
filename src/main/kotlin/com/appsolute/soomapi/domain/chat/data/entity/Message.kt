package com.appsolute.soomapi.domain.chat.data.entity

import com.appsolute.soomapi.domain.chat.data.response.HttpMessageResponse
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.chat.util.GetDataUtil
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.response.MessageResponse
import com.appsolute.soomapi.domain.chat.data.response.SocketMessageResponse
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
class Message(
    content: String,
    sender: User?,
    chatRoom: ChatRoom,
    type: MessageType

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    private val content: String = content
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val sender: User? = sender
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val chatRoom: ChatRoom = chatRoom

    private val type: MessageType = type

    private val sentAt: LocalDateTime = LocalDateTime.now()

    private val isDelete: Boolean = false

    @Transient
    private lateinit var getDataUtil: GetDataUtil

    fun toHttpMessageResponse(user: User): HttpMessageResponse{
        return HttpMessageResponse(
            this.id.toString(),
            sender?.let {
                MessageResponse.Sender(
                    it.getLastName(),
                    sender.uuid
                )
            },
            this.content,
            this.sentAt,
            this.chatRoom.id,
            this.type,
            user.uuid == sender?.uuid,
            this.isDelete
        )
    }

    fun toSocketMessageResponse(): SocketMessageResponse{
        return SocketMessageResponse(
            this.id.toString(),
            sender?.let {
                MessageResponse.Sender(
                    it.getLastName(),
                    sender.uuid
                )
            },
            this.content,
            this.sentAt,
            this.chatRoom.id,
            this.type
        )
    }

}
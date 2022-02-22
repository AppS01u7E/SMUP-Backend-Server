package com.appsolute.soomapi.domain.chat.data.entity

import com.appsolute.soomapi.domain.chat.data.response.HttpMessageResponse
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.chat.util.GetDataUtil
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.response.MessageResponse
import com.appsolute.soomapi.domain.chat.data.response.SocketMessageResponse
import com.appsolute.soomapi.domain.chat.data.type.ContentType
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
class Message(
    content: String,
    sender: User?,
    chatRoom: ChatRoom,
    type: MessageType,
    contentType: ContentType

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    private var content: String = content
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val sender: User? = sender
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val chatRoom: ChatRoom = chatRoom

    private val type: MessageType = type
    @Enumerated(EnumType.STRING)
    private val contentType: ContentType = contentType

    private val sentAt: LocalDateTime = LocalDateTime.now()

    private var isDelete: Boolean = false

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
            if (this.isDelete) {
                "삭제됨"
            } else {
                this.content
            },
            this.sentAt,
            this.type,
            user.uuid == sender?.uuid,
            this.isDelete
        )
    }

    fun toGroupHttpMessageResponse(group: Soom): HttpMessageResponse {
        return HttpMessageResponse(
            this.id.toString(),
            sender?.let {
                MessageResponse.Sender(
                    it.getLastName(),
                    sender.uuid
                )
            },
            if (this.isDelete) {
                "삭제됨"
            } else {
                this.content
            },
            this.sentAt,
            this.type,
            group.memberList.contains(sender),
            this.isDelete
        )
    }


    fun makeDelete(): Message {
        this.isDelete = true
        return this
    }

    fun editContent(content: String): Message{
        this.content = content
        return this
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
            this.type
        )
    }

}
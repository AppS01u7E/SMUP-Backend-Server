package com.appsolute.soomapi.domain.chat.repository

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.entity.Message
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageRepository: JpaRepository<Message, Int> {

    fun findByIdAndSender(id: Int, sender: User): Optional<Message>
    fun findAllByChatRoom(chatRoom: ChatRoom, pageable: Pageable): List<Message>

}
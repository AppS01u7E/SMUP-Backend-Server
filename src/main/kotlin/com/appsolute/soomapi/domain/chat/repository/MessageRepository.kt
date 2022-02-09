package com.appsolute.soomapi.domain.chat.repository

import com.appsolute.soomapi.domain.chat.data.entity.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Int> {


}
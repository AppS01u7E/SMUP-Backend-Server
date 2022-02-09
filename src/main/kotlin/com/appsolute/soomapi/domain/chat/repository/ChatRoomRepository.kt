package com.appsolute.soomapi.domain.chat.repository

import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, String> {


}
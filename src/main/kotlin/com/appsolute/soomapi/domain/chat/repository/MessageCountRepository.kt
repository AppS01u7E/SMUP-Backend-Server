package com.appsolute.soomapi.domain.chat.repository

import com.appsolute.soomapi.domain.chat.data.entity.MessageCount
import org.springframework.data.repository.CrudRepository

interface MessageCountRepository: CrudRepository<MessageCount, String> {
}
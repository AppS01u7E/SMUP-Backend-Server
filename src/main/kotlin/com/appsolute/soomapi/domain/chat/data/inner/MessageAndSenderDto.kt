package com.appsolute.soomapi.domain.chat.data.inner

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.entity.Message

data class MessageAndSenderDto(
    val sender: User,
    val message: Message

)

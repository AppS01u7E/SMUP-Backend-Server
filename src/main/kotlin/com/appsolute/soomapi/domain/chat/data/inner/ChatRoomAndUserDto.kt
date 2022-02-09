package com.appsolute.soomapi.domain.chat.data.inner

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom

data class ChatRoomAndUserDto (
    val user: User,
    val chatRoom: ChatRoom

)
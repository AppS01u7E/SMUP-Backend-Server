package com.appsolute.soomapi.domain.chat.util

import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.inner.ChatRoomAndUserDto
import com.appsolute.soomapi.domain.chat.exception.ChatRoomCannotFounException
import com.appsolute.soomapi.domain.chat.repository.ChatRoomRepository
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.stereotype.Component


@Component
class CheckChatRoomUtil(
    private val chatRoomRepository: ChatRoomRepository,
    private val current: CurrentUser

) {

    fun checkIsChatRoomMember(chatRoomId: String): ChatRoomAndUserDto{
        val chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null)?: throw ChatRoomCannotFounException(chatRoomId)
        val user = current.getUser()
        if (chatRoom.isMember(user)) return ChatRoomAndUserDto(
            user,
            chatRoom
        )
        else throw IsNotGroupMemberException(current.getPk())
    }


}
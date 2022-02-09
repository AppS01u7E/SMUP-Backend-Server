package com.appsolute.soomapi.domain.chat.util

import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.error.base.ChatErrorCode
import com.appsolute.soomapi.domain.chat.error.base.ChatExceptionResponse
import com.appsolute.soomapi.domain.chat.error.handler.ChatExceptionHandler
import com.appsolute.soomapi.domain.chat.repository.ChatRoomRepository
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.corundumstudio.socketio.SocketIOClient
import org.hibernate.exception.DataException
import org.springframework.stereotype.Component


@Component
class GetDataUtil(
    private val chatExceptionHandler: ChatExceptionHandler,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val exception: ChatExceptionHandler,
    private val chatRoomRepository: ChatRoomRepository
) {

    fun findUser(client: SocketIOClient): User {
        val userId = client.get<String>("user")
        val user: User? = userRepository.findById(userId).orElse(null)
        user?: let{
            chatExceptionHandler.errorAndDisconnected(client, userId, ChatExceptionResponse(ChatErrorCode.USER_NOT_FOUND))
        }
        return user!!
    }

    fun findGroup(groupId: String): Soom?{
        return findGroup(groupId, null)
    }


    fun findGroup(groupId: String, header: User?): Soom?{
        var group: Soom? = null
        try {
            header?.let {
                group = groupRepository.findByIdAndHeader(groupId, header).orElse(null)
            }?:let {
                group = groupRepository.findById(groupId).orElse(null)
            }
        } catch (e: DataException){
            return null
        }
        return group
    }


    fun findChatRoom(chatRoomId: String): ChatRoom? {
        val chatRoom: ChatRoom
        try {
            chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null)
        } catch (e: DataException) {
            return null
        }
        return chatRoom
    }


}
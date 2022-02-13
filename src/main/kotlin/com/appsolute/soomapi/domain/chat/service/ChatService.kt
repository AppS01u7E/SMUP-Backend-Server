package com.appsolute.soomapi.domain.chat.service

import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.response.ChatRoomListResponse
import com.appsolute.soomapi.domain.chat.data.response.OneChatRoomResponse
import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service

interface ChatService {

    fun sendMessage(client: SocketIOClient, json: String)
    fun deleteChatRoom(chatRoom: ChatRoom)

    fun getMessage(chatRoomId: String, idx: Int, size: Int): OneChatRoomResponse
    fun getChatRoomList(): ChatRoomListResponse
    fun deleteMessage(messageId: String)
    fun editMessage(messageId: String, content: String)


}
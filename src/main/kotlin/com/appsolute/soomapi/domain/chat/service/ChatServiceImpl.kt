package com.appsolute.soomapi.domain.chat.service

import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.entity.Message
import com.appsolute.soomapi.domain.chat.data.request.SendMessageRequest
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.chat.error.base.ChatErrorCode
import com.appsolute.soomapi.domain.chat.error.base.ChatExceptionResponse
import com.appsolute.soomapi.domain.chat.error.handler.ChatExceptionHandler
import com.appsolute.soomapi.domain.chat.repository.MessageRepository
import com.appsolute.soomapi.domain.chat.util.GetDataUtil
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.MessageCount
import com.appsolute.soomapi.domain.chat.data.response.ChatRoomListResponse
import com.appsolute.soomapi.domain.chat.data.response.MessageResponse
import com.appsolute.soomapi.domain.chat.data.response.OneChatRoomResponse
import com.appsolute.soomapi.domain.chat.data.response.SocketMessageResponse
import com.appsolute.soomapi.domain.chat.exception.ChatRoomCannotFounException
import com.appsolute.soomapi.domain.chat.repository.ChatRoomRepository
import com.appsolute.soomapi.domain.chat.repository.MessageCountRepository
import com.appsolute.soomapi.domain.chat.util.CheckChatRoomUtil
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.fcm.FcmService
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.exception.DataException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ChatServiceImpl(
    private val getDataUtil: GetDataUtil,
    private val chatExceptionHandler: ChatExceptionHandler,
    private val server: SocketIOServer,
    private val messageRepository: MessageRepository,
    private val fcmService: FcmService,
    private val checkChatRoomUtil: CheckChatRoomUtil,
    private val current: CurrentUser,
    private val messageCountRepository: MessageCountRepository

): ChatService {

    private val objectMapper: ObjectMapper = ObjectMapper()


    override fun sendMessage(client: SocketIOClient, json: String) {
        val user: User = getDataUtil.findUser(client)
        val request: SendMessageRequest

        try{
            request = objectMapper.readValue(json, SendMessageRequest::class.java)
            request.checkIsNotSystemMessage()?: return chatExceptionHandler.errorAndDisconnected(client, null, ChatExceptionResponse(
                    ChatErrorCode.LOW_AUTHENTICATION
                )
            )
        } catch (e: DataException){
            return chatExceptionHandler.errorAndDisconnected(client, user.uuid, ChatExceptionResponse(
                ChatErrorCode.IMPORTING_DATA_ERROR
            ))
        }
        val chatRoom: ChatRoom
        try{
            chatRoom = checkChatRoomUtil.checkIsChatRoomMember(request.chatRoomId).chatRoom
        } catch (e: Exception){
            chatExceptionHandler.errorAndDisconnected(client, user.uuid, ChatExceptionResponse(
                ChatErrorCode.CHAT_ROOM_NOT_EXISTS
            ))
            return
        }

        val message = Message(
            request.message,
            user,
            chatRoom,
            request.messageType
        )
        messageRepository.save(
            message
        )
        val userList: List<User> = server.getRoomOperations(chatRoom.id).clients.stream().map {
            getDataUtil.findUser(it)
        }.toList()

        fcmService.sendChatRoomAlarm(
            chatRoom, userList,
            "새로운 메시지가 존재합니다.",
            "${getDataUtil.findUser(client).getFirstName()}님이 메시지를 보냈습니다."
        )

        send(chatRoom.id, message.toSocketMessageResponse())
    }

    @Transactional
    override fun deleteChatRoom(chatRoom: ChatRoom) {
        val message = Message(
            "${chatRoom.getName()}이 삭제되었습니다.",
            null,
            chatRoom,
            MessageType.SYSTEM
        )
        messageRepository.save(message)

        server.getRoomOperations(chatRoom.id).sendEvent(
            "info",
                message.toSocketMessageResponse()
        )
        chatRoom.makeDone()
    }

    override fun getMessage(chatRoomId: String, idx: Int, size: Int): OneChatRoomResponse{
        val dto = checkChatRoomUtil.checkIsChatRoomMember(chatRoomId)
        val chatRoom = dto.chatRoom
        val user = dto.user

        return OneChatRoomResponse(
            idx,
            size,
            messageRepository.findAll(PageRequest.of(idx, size)).stream().map {
                it.toHttpMessageResponse(user)
            }.toList()
        )

    }

    override fun getChatRoomList(): ChatRoomListResponse {
        val user = current.getUser()

        return ChatRoomListResponse(
            user.getChatRoomList().size,
            user.getChatRoomList().stream().map {
                it.toChatRoomResponse(messageCountRepository.findById(
                    user.uuid + it.id + "messageCount"
                ).orElse(null)?: throw InternalError())
            }.toList()
        )
    }

    private fun send(chatRoomId: String, response: SocketMessageResponse){
        var event: String = "message"
        if (response.messageType.equals(MessageType.SYSTEM)) event = "info"
        server.getRoomOperations(chatRoomId).sendEvent(
            event,
            response
        )
    }

}
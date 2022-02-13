package com.appsolute.soomapi.domain.chat.service

import com.appsolute.soomapi.domain.chat.error.base.ChatErrorCode
import com.appsolute.soomapi.domain.chat.error.base.ChatExceptionResponse
import com.appsolute.soomapi.domain.chat.error.handler.ChatExceptionHandler
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.chat.repository.MessageCountRepository
import com.appsolute.soomapi.domain.chat.util.GetDataUtil
import com.appsolute.soomapi.global.security.util.AccessTokenUtil
import com.corundumstudio.socketio.SocketIOClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.logging.SocketHandler
import javax.transaction.Transactional

@Service
class ConnectServiceImpl(
    private val tokenProvider: AccessTokenUtil,
    private val userRepository: UserRepository,
    private val chatExceptionHandler: ChatExceptionHandler,
    private val messageCountRepository: MessageCountRepository,
    private val getDataUtil: GetDataUtil

): ConnectService {

    private val objectMapper: ObjectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)


    override fun connect(client: SocketIOClient) {
        val token: String = client.handshakeData.getSingleUrlParam("authorization")
        var userId: String = ""
        try {
             userId =
                (tokenProvider.decodeToken(token) ?: chatExceptionHandler.errorAndDisconnected(
                    client, null, ChatExceptionResponse(
                        ChatErrorCode.INVALID_TOKEN
                    )
                )) as String
        } catch (e: Exception) {
            chatExceptionHandler.errorAndDisconnected(
                client, null, ChatExceptionResponse(
                    ChatErrorCode.INVALID_TOKEN
            ))
        }
        val user: User = userRepository.findById(userId).orElse(null)?: return chatExceptionHandler.errorAndDisconnected(
            client,
            userId,
            ChatExceptionResponse(
                ChatErrorCode.USER_NOT_FOUND
            )
        )
        client.set("user", userId)
        log.info("connect: " + userId + "/" + user.getEmail() + "-> sessionId: " + client.sessionId)

        for (chatRoom in (user.getChatRoomList().filter { chatRoom -> chatRoom.checkIsDone() != null })) {
            client.joinRoom(chatRoom.id)
        }
    }

    @Transactional
    override fun disconnect(client: SocketIOClient) {
        client.sendEvent("info", "success to disconnect")

        try {
            val user = getDataUtil.findUser(client)

            user.getChatRoomList().forEach {
                messageCountRepository.findById(user.uuid + it.id).get().messageCount = it.chatCount
            }
        } catch (e: Exception) {
            client.disconnect()
        }
        client.disconnect()
    }

}
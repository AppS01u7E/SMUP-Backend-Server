package com.appsolute.soomapi.domain.chat.error.handler

import com.appsolute.soomapi.domain.chat.error.base.ChatExceptionResponse
import com.corundumstudio.socketio.SocketIOClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ChatExceptionHandler(
) {
    private val log = LoggerFactory.getLogger(javaClass)


    fun errorAndDisconnected(client: SocketIOClient, userId: String?, exception: ChatExceptionResponse){
        client.sendEvent("error", exception)
        log.warn("sessionId: "+ client.sessionId + " /userId: " + userId + " /error: " + exception.data)
        client.disconnect()
        return
    }

}
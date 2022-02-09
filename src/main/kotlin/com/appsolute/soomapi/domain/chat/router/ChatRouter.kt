package com.appsolute.soomapi.domain.chat.router

import com.appsolute.soomapi.domain.chat.service.ChatService
import com.appsolute.soomapi.domain.chat.service.ConnectService
import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ChatRouter(
    private val server: SocketIOServer,
    private val chatService: ChatService,
    private val socketService: ConnectService
) {


    @PostConstruct
    fun router() {
        server.addConnectListener(socketService::connect)
        server.addDisconnectListener(socketService::disconnect)

        server.addEventListener("sendMessage", String::class.java){
            client: SocketIOClient, data: String, ackSender: AckRequest ->
            chatService.sendMessage(
                client,
                data
            )
        }




    }

}
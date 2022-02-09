package com.appsolute.soomapi.domain.chat.service

import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service


interface ConnectService {

    fun connect(client: SocketIOClient)
    fun disconnect(client: SocketIOClient)

}
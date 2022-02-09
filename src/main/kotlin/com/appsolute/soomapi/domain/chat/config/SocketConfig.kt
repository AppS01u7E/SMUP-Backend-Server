package com.appsolute.soomapi.domain.chat.config

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.Transport
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class SocketConfig(
    private val property: SocketProperty
) {
    private var server: SocketIOServer? = null

    @Bean
    fun SocketIOServer(): SocketIOServer {
        val configuration = Configuration()
        configuration.port = property.port
        configuration.setTransports(Transport.POLLING, Transport.WEBSOCKET)
        configuration.origin = "*"

        val server = SocketIOServer(configuration)
        this.server = server
        return server
    }
}

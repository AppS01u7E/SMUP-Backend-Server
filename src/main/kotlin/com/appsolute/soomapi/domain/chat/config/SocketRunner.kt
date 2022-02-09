package com.appsolute.soomapi.domain.chat.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


@Component
@Order(1)
class SocketRunner(
    private val server: SocketIOServer
): CommandLineRunner {

    override fun run(vararg args: String?) {
        server.start()
    }
}

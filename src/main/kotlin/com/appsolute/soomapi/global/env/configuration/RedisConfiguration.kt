package com.appsolute.soomapi.global.env.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfiguration {
    @Value("#{spring.redis.host}")
    private val host: String? = null

    @Value("#{spring.redis.port}")
    private val port = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        return LettuceConnectionFactory(host!!, port)
    }
}
package com.appsolute.soomapi.global.env.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport


@Configuration
class FilterConfiguration: WebMvcConfigurationSupport() {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)

//        registry.addInterceptor()

    }
}
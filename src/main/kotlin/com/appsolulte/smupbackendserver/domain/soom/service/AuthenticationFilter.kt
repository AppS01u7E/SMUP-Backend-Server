package com.appsolulte.smupbackendserver.domain.soom.service

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import javax.servlet.*
import javax.servlet.annotation.WebFilter

@Component
@WebFilter(
    description = "Soom 권한 필터",
    urlPatterns = "/api/soom/",
    filterName = "SoomAuthFilter"
)
class AuthenticationFilter: Filter {

    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {




    }

}
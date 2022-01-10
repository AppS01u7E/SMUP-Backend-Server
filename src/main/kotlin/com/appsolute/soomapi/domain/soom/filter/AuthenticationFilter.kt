package com.appsolute.soomapi.domain.soom.filter

import org.springframework.stereotype.Component
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
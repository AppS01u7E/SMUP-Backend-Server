package com.appsolute.soomapi.global.security.filter

import com.appsolute.soomapi.domain.account.util.EmailJwtUtils
import com.appsolute.soomapi.global.security.exception.ExpiredTokenException
import com.appsolute.soomapi.global.security.service.CustomUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val emailJwtUtils: EmailJwtUtils,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        getToken(request)?.let {
            val subject = emailJwtUtils.decodeToken(it)
            val userDetails = customUserDetailsService.loadUserByUsername(subject)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, subject, userDetails.authorities)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (bearerToken.startsWith("Bearer ")) return bearerToken.substring(7)
        throw ExpiredTokenException()
    }
}

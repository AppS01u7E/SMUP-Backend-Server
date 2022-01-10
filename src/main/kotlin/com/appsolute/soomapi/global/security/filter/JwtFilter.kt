package com.appsolute.soomapi.global.security.filter

import com.appsolute.soomapi.global.security.util.TokenProvider
import com.appsolute.soomapi.global.security.exception.ExpiredTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        getToken(request)?.let {
            tokenProvider.validateToken(it)
            val authentication = tokenProvider.getAuthentication(it)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (bearerToken.startsWith("Bearer ")) return bearerToken.substring(7)
        throw ExpiredTokenException()
    }
}

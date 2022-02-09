package com.appsolute.soomapi.global.security.filter

import com.appsolute.soomapi.global.error.response.ExceptionResponse
import com.appsolute.soomapi.global.security.exception.ExpiredTokenException
import com.appsolute.soomapi.global.security.exception.InvalidTokenException
import com.appsolute.soomapi.global.security.service.CustomUserDetailsService
import com.appsolute.soomapi.global.security.util.AccessTokenUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.mail.internet.ContentType
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val jwtUtils: AccessTokenUtil,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    private val objectMapper = ObjectMapper()


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            getToken(request)?.let {
                val subject = jwtUtils.decodeToken(it)
                val userDetails = customUserDetailsService.loadUserByUsername(subject)
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(userDetails, subject, userDetails.authorities)
            }
            filterChain.doFilter(request, response)
        } catch (e: InvalidTokenException){
            response.status = e.errorCode.status.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            objectMapper.writeValue(response.writer, ExceptionResponse(
                e.message.toString(),
                e.data
            ))
        }

    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (bearerToken.startsWith("Bearer ")) return bearerToken.substring(7)
        throw ExpiredTokenException(bearerToken)
    }
}

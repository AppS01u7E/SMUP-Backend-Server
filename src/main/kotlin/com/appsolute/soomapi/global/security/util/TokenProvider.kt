package com.appsolute.soomapi.global.security.util

import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.global.env.property.GlobalProperties
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import com.appsolute.soomapi.global.security.exception.ExpiredTokenException
import com.appsolute.soomapi.global.security.exception.InvalidTokenException
import com.appsolute.soomapi.global.security.service.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@kotlin.Deprecated(message = "StandardJwtUtils 를 구현하여 사용해주세요", level = DeprecationLevel.ERROR)
@Service
class TokenProvider(
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val globalProperties: GlobalProperties
) {
    private val security = globalProperties.security

    private val log = LoggerFactory.getLogger(javaClass)



    fun generateToken(salt: String): TokenResponse {
        val claims: Claims = Jwts.claims().setSubject(salt).setExpiration(Date(System.currentTimeMillis() + (security.accessExpire)))

        return TokenResponse(
            Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, security.secretKey)
                .setExpiration(Date(System.currentTimeMillis() + security.accessExpire))
                .compact(),
            Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, security.secretKey)
                .setExpiration(Date(System.currentTimeMillis() + security.refreshExpire))
                .compact()
        )
    }

    fun validateToken(token: String): Unit{
        try {
            if (!Jwts.parser().setSigningKey(security.secretKey).parseClaimsJws(token).body.expiration.after(
                    Date(System.currentTimeMillis())
                )
            ) throw ExpiredTokenException()
        } catch (e: SignatureException) {
            throw InvalidTokenException()
        }
    }

    fun reissue(tokenResponse: TokenResponse): TokenResponse {
        val salt: String
        try{
            validateToken(tokenResponse.refreshToken)
            salt = Jwts.parser().setSigningKey(security.secretKey).parseClaimsJws(tokenResponse.accessToken).body.subject
        } catch (e: SignatureException) {
            throw InvalidTokenException()
        }
        return userRepository.findById(salt).map { generateToken(salt)}
            .orElse(null)?: throw ExpiredTokenException()
    }

    fun getTokenUser(accessToken: String): String{
        return Jwts.parser().setSigningKey(security.secretKey).parseClaimsJws(accessToken).body.subject
    }

    fun getAuthentication(token: String): Authentication {
        val id = getTokenUser(token)
        val userDetails = customUserDetailsService.loadUserByUsername(id)
        return UsernamePasswordAuthenticationToken(userDetails, id, userDetails.authorities)
    }


}
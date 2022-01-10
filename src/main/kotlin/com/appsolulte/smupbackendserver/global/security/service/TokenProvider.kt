package com.appsolulte.smupbackendserver.global.security.service

import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import com.appsolulte.smupbackendserver.global.configuration.GlobalProperties
import com.appsolulte.smupbackendserver.global.security.dto.response.TokenResponse
import com.appsolulte.smupbackendserver.global.security.exception.ExpiredTokenException
import com.appsolulte.smupbackendserver.global.security.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

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

    fun reissue(tokenResponse: TokenResponse): TokenResponse{
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
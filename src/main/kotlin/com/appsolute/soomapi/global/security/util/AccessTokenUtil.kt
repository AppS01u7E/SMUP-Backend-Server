package com.appsolute.soomapi.global.security.util

import com.appsolute.soomapi.global.env.property.JwtProperty
import com.appsolute.soomapi.global.jwt.util.StandardJwtUtils
import com.appsolute.soomapi.global.security.data.CustomUserDetails
import com.appsolute.soomapi.global.security.service.CustomUserDetailsService
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class AccessTokenUtil(
    private val jwtProperty: JwtProperty,
    private val customUserDetailsService: CustomUserDetailsService
): StandardJwtUtils<String>() {


    override fun getExpiredAt(now: LocalDateTime): LocalDateTime {
        return now.plusSeconds(jwtProperty.accessExpiredAt)
    }

    override fun getClaims(userPk: String): Map<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims["userPk"] = userPk
        return claims
    }

    override fun getSecret(): String {
        return jwtProperty.secret
    }

    override fun getDataFromClaims(claims: Claims): String {
        return claims.get("userPk", String::class.java)
    }

    override fun loadUserByUsername(username: String): CustomUserDetails {
        return customUserDetailsService.loadUserByUsername(username)
    }

}
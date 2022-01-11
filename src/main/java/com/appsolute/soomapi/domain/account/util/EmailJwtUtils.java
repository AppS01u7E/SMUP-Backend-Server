package com.appsolute.soomapi.domain.account.util;

import com.appsolute.soomapi.global.env.property.JwtProperty;
import com.appsolute.soomapi.global.jwt.util.StandardJwtUtils;
import com.appsolute.soomapi.global.security.data.CustomUserDetails;
import com.appsolute.soomapi.global.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailJwtUtils extends StandardJwtUtils<String> {
    private final JwtProperty jwtProperty;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected LocalDateTime getExpiredAt(final LocalDateTime now) {
        return now.plusSeconds(jwtProperty.getAccessExpiredAt());
    }
    @Override
    protected Map<String, Object> getClaims(final String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    }

    @Override
    protected String getSecret() {
        return jwtProperty.getSecret();
    }
    @Override
    protected String getDataFromClaims(final Claims claims) {
        return claims.get("email", String.class);
    }

    @Override
    protected CustomUserDetails loadUserByUsername(String username) {
        return customUserDetailsService.loadUserByUsername(username);
    }
}

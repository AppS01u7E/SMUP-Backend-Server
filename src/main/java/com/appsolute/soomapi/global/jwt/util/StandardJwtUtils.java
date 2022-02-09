package com.appsolute.soomapi.global.jwt.util;

import com.appsolute.soomapi.global.security.data.CustomUserDetails;
import com.appsolute.soomapi.global.security.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
public abstract class StandardJwtUtils<T> implements JwtUtils<T> {

    @Override
    public String encodeToken(T data) {
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
                .setClaims(getClaims(data))
                .signWith(SignatureAlgorithm.HS256, getSecret())
                .setIssuedAt(Timestamp.valueOf(now))
                .setExpiration(Timestamp.valueOf(getExpiredAt(now)))
                .compact();
    }

    @Override
    public T decodeToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            throw new InvalidTokenException(token);
        }
        return getDataFromClaims(claims);
    }


    protected abstract LocalDateTime getExpiredAt(LocalDateTime now);
    protected abstract Map<String, Object> getClaims(T data);
    protected abstract String getSecret();

    protected abstract T getDataFromClaims(Claims claims);
    protected abstract CustomUserDetails loadUserByUsername(String username);
}

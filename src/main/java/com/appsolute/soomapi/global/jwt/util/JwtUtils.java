package com.appsolute.soomapi.global.jwt.util;

import com.appsolute.soomapi.global.security.data.response.TokenResponse;

public interface JwtUtils<T> {
    String encodeToken(T data);
    T decodeToken(String token);
}
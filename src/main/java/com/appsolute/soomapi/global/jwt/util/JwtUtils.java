package com.appsolute.soomapi.global.jwt.util;

public interface JwtUtils<T> {
    String encodeToken(T data);
    T decodeToken(String token);
}
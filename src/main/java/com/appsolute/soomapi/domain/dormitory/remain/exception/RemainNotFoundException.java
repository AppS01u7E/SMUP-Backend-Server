package com.appsolute.soomapi.domain.dormitory.remain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemainNotFoundException extends RuntimeException {
    private final String accountUUID;
}

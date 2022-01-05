package com.appsolulte.smupbackendserver.global.exception.base

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {
    USER_NOT_FOUND("해당 user룰 조회하지 못하였습니다.", HttpStatus.NOT_FOUND),
    DEVICE_TOKEN_NOT_FOUND("user의 deviceToken을 찾지 못했습니다.", HttpStatus.NOT_FOUND)


}
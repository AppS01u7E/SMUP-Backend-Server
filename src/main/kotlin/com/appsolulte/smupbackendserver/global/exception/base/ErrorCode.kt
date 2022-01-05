package com.appsolulte.smupbackendserver.global.exception.base

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {
    USER_NOT_FOUND("해당 user룰 조회하지 못하였습니다.", HttpStatus.NOT_FOUND)


}
package com.appsolute.soomapi.global.security.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidTokenException(data: String): GlobalException(ErrorCode.INVALID_TOKEN, data) {
}
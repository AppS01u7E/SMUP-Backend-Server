package com.appsolute.soomapi.global.security.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class InvalidTokenException: GlobalException(ErrorCode.INVALID_TOKEN) {
}
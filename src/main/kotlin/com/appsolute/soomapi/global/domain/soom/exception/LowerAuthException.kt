package com.appsolute.soomapi.global.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class LowerAuthException: GlobalException(ErrorCode.LOW_AUTH) {
}
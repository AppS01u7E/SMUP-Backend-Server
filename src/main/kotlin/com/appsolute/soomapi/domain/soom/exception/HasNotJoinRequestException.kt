package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class HasNotJoinRequestException(data: String): GlobalException(ErrorCode.HAS_NOT_JOIN_REQUEST, data) {
}
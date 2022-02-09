package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class PostCannotFoundException(data: String): GlobalException(ErrorCode.POST_CANNOT_FOUND, data) {
}
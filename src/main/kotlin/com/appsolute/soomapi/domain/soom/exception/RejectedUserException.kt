package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class RejectedUserException(data: String): GlobalException(ErrorCode.REJECTED_USER_EXCEPTION, data) {
}
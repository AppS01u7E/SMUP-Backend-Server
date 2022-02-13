package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class SameNameGroupAlreadyExsitsException(data: String): GlobalException(ErrorCode.SAME_NAME_ALREADY_EXISTS, data) {
}
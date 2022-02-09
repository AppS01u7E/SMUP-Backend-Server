package com.appsolute.soomapi.domain.account.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class IncorrectPasswordException(data: String): GlobalException(ErrorCode.INCORRECT_PASSWORD, data) {
}

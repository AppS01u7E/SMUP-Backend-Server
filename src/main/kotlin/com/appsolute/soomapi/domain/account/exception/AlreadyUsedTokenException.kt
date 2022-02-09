package com.appsolute.soomapi.domain.account.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class AlreadyUsedTokenException(data: String): GlobalException(ErrorCode.ALREADY_USED_EMAIL, data) {
}
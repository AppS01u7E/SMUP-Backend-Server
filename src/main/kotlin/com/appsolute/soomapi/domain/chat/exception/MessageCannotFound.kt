package com.appsolute.soomapi.domain.chat.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class MessageCannotFound(data: String): GlobalException(ErrorCode.MESSAGE_NOT_FOUND, data) {
}
package com.appsolute.soomapi.global.error.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode

class AlreadyAcceptedRequestException(data: String): GlobalException(ErrorCode.ALREADY_ACCEPTED_REQUEST, data) {
}
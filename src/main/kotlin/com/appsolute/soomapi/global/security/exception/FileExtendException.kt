package com.appsolute.soomapi.global.security.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class FileExtendException(data: String): GlobalException(ErrorCode.SERIOUS_SECURITY_THREAT_EXISTS, data) {
}
package com.appsolute.soomapi.infra.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class FileSecurityDetectedException(data: String): GlobalException(ErrorCode.SERIOUS_SECURITY_THREAT_EXISTS, data) {
}
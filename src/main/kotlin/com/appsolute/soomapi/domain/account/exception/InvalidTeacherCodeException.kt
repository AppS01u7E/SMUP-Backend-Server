package com.appsolute.soomapi.domain.account.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class InvalidTeacherCodeException(code: String): GlobalException(ErrorCode.INVALID_TEACHER_CODE, code) {
}
package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class TeacherAlreadyExistsException(data: String): GlobalException(ErrorCode.TEACHER_ALREADY_EXISTS, data) {
}
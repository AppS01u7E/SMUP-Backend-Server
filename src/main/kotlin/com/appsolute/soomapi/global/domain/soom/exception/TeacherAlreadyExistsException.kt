package com.appsolute.soomapi.global.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class TeacherAlreadyExistsException: GlobalException(ErrorCode.TEACHER_ALREADY_EXISTS) {
}
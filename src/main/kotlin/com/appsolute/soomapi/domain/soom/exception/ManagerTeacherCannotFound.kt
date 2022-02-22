package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class ManagerTeacherCannotFound(data: String): GlobalException(ErrorCode.MANAGER_TEACHER_CANNOT_FOUND, data) {
}
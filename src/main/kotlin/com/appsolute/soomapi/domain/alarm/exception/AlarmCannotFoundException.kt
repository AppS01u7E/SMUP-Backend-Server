package com.appsolute.soomapi.domain.alarm.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class AlarmCannotFoundException(id: String): GlobalException(ErrorCode.ALARM_NOT_FOUND, id) {
}
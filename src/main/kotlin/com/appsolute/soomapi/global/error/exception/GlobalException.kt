package com.appsolute.soomapi.global.error.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode

open class GlobalException(val errorCode: ErrorCode): RuntimeException(errorCode.message) {
}
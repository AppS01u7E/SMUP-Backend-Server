package com.appsolulte.smupbackendserver.global.exception.base

open class GlobalException(val errorCode: ErrorCode): RuntimeException(errorCode.message) {
}
package com.appsolute.soomapi.global.error.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import lombok.Getter
import lombok.Setter


@Getter
@Setter
open class GlobalException(val errorCode: ErrorCode, val data: String): RuntimeException(errorCode.message) {
}
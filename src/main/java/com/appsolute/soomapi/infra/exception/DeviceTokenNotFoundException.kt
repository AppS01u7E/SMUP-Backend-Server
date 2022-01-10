package com.appsolute.soomapi.infra.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class DeviceTokenNotFoundException: GlobalException(ErrorCode.DEVICE_TOKEN_NOT_FOUND) {
}
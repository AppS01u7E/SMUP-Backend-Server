package com.appsolulte.smupbackendserver.global.firebase.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class DeviceTokenNotFoundException: GlobalException(ErrorCode.DEVICE_TOKEN_NOT_FOUND) {
}
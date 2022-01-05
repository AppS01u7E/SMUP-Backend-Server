package com.appsolulte.smupbackendserver.global.security.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class ExpiredTokenException: GlobalException(ErrorCode.EXPIRED_TOKEN) {
}
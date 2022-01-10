package com.appsolulte.smupbackendserver.domain.soom.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class LowerAuthException: GlobalException(ErrorCode.LOW_AUTH) {
}
package com.appsolulte.smupbackendserver.domain.soom.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class RejectedUserException: GlobalException(ErrorCode.REJECTED_USER_EXCEPTION) {
}
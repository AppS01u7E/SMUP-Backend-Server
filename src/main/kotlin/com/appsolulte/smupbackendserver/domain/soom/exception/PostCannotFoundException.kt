package com.appsolulte.smupbackendserver.domain.soom.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class PostCannotFoundException: GlobalException(ErrorCode.POST_CANNOT_FOUND) {
}
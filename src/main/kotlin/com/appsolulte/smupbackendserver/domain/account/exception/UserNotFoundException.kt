package com.appsolulte.smupbackendserver.domain.account.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class UserNotFoundException: GlobalException(ErrorCode.USER_NOT_FOUND) {
}
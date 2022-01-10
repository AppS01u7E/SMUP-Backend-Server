package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class GroupCannotFoundException: GlobalException(ErrorCode.GROUP_NOT_FOUND) {

}
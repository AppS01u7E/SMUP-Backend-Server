package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class IsAlreadyGroupMmeberException(data: String): GlobalException(ErrorCode.ALREADY_GROUP_MEMBER, data) {
}
package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class IsNotGroupMemberException(data: String): GlobalException(ErrorCode.IS_NOT_GROUP_MEMBER, data) {
}
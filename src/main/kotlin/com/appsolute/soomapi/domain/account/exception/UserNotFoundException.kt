package com.appsolute.soomapi.domain.account.exception


import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException
import lombok.Getter
import lombok.Setter


@Getter
@Setter
class UserNotFoundException(data: String): GlobalException(ErrorCode.USER_NOT_FOUND, data) {
}
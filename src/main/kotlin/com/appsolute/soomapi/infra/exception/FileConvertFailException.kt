package com.appsolute.soomapi.infra.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class FileConvertFailException(data: String): GlobalException(ErrorCode.FILE_CONVERT_ERROR, data) {
}
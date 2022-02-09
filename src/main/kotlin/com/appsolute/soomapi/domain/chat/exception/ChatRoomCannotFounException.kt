package com.appsolute.soomapi.domain.chat.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class ChatRoomCannotFounException(data: String): GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND, data) {
}
package com.appsolute.soomapi.domain.chat.error.base

import io.netty.handler.codec.http.HttpStatusClass

enum class ChatErrorCode(
    val message: String,
    val code: HttpStatusClass
) {
    INVALID_TOKEN("토큰이 올바르지 않습니다.", HttpStatusClass.CLIENT_ERROR),
    USER_NOT_FOUND("유저를 찾지 못하였습니다.", HttpStatusClass.UNKNOWN),
    JSON_PARSING_ERROR("json data를 파싱하는 과정에서 에러가 발생했습니다.", HttpStatusClass.CLIENT_ERROR),
    GROUP_NOT_EXISTS("존재하지 않는 그룹입니다.", HttpStatusClass.UNKNOWN),
    IMPORTING_DATA_ERROR("데이터를 가져오는 데에 실패하였습니다.", HttpStatusClass.CLIENT_ERROR),
    CHAT_ROOM_NOT_EXISTS("대화방이 존재하지 않습니다.", HttpStatusClass.UNKNOWN),
    LOW_AUTHENTICATION("해당 명령을 실행하기에 낮은 권한입니다.", HttpStatusClass.CLIENT_ERROR),
    CLOSED_CHATTING_ROOM("닫힌 대화방입니다.", HttpStatusClass.CLIENT_ERROR)




}
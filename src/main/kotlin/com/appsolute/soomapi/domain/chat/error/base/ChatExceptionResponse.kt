package com.appsolute.soomapi.domain.chat.error.base

class ChatExceptionResponse (
    chatErrorCode: ChatErrorCode,

    ){
    val errorCode = chatErrorCode
    val data = chatErrorCode.message
}
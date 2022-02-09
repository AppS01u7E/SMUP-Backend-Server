package com.appsolute.soomapi.global.error.response

import lombok.Getter
import lombok.Setter
import org.springframework.http.HttpStatus


@Getter
@Setter
data class ExceptionResponse (
    val message: String,
    val data: String?

){
}


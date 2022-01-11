package com.appsolute.soomapi.global.error.handler

import com.appsolute.soomapi.global.error.exception.GlobalException
import com.appsolute.soomapi.global.security.CurrentUser
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController


@ControllerAdvice
@RestController
class ExceptionHandler(
    private val current: CurrentUser
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(GlobalException::class)
    fun GlobalExceptionHandler(e: GlobalException): ResponseEntity<*>{
        val user = current.getUser()
        log.info("user: ${user.id}(${user.getEmail()}) errorMessage: ${e.errorCode.message}")
        return ResponseEntity.status(e.errorCode.status.value()).body(e.errorCode.message)
    }


}
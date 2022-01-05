package com.appsolulte.smupbackendserver.global.exception.base

import com.appsolulte.smupbackendserver.global.facade.CurrentUser
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController


@ControllerAdvice
@RestController
class ExceptionHanlder(
    private val current: CurrentUser
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(GlobalException::class)
    fun GlobalExceptionHandler(e: GlobalException): ResponseEntity<*>{
        val user = current.get()
        log.info("user: ${user.getId()}(${user.getEmail()}) errorMessage: ${e.errorCode.message}")
        return ResponseEntity.status(e.errorCode.status.value()).body(e.errorCode.message)
    }


}
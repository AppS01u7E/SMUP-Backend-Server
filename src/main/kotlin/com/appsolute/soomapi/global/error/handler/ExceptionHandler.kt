package com.appsolute.soomapi.global.error.handler

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.global.error.exception.GlobalException
import com.appsolute.soomapi.global.error.response.ExceptionResponse
import com.appsolute.soomapi.global.security.CurrentUser
import com.sun.mail.iap.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.GeneralSecurityException
import java.security.SignatureException


@RestControllerAdvice
class ExceptionHandler(
    private val current: CurrentUser
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(GlobalException::class)
    fun globalExceptionHandler(e: GlobalException): ResponseEntity<*>{
        var user: User
        try {
            user = current.getUser()
            log.info("user: ${user.uuid}(${user.getEmail()})/errorMessage: ${e.errorCode.message}")
        } catch (err: Exception){
            log.info("user: null/errorMessage: ${e.errorCode.message}")
        }
        return ResponseEntity.status(e.errorCode.status.value()).body(ExceptionResponse(e.errorCode.message, e.data))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validationExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<*>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse(e.message, "입력 값 오류"))
    }

    @ExceptionHandler(SignatureException::class)
    fun securityExceptionHandler(e: GeneralSecurityException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponse(
            e.message.toString(),
            e.cause.toString()
        ))
    }


}
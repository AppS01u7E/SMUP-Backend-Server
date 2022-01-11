package com.appsolute.soomapi.domain.account.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/account")
class AccountController(

) {

    @GetMapping("/test")
    fun test(): Unit{

    }



}
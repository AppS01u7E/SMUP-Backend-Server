package com.appsolulte.smupbackendserver.domain.account.controller

import com.appsolulte.smupbackendserver.domain.account.repository.StudentRepository
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import net.bytebuddy.utility.RandomString
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random


@RestController
@RequestMapping("/api/account")
class AccountController(

) {

    @GetMapping("/test")
    fun test(): String{
        return userRepository.findById("2223").isEmpty.toString()
    }



}
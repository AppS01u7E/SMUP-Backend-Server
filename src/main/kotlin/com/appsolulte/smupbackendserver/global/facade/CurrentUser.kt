package com.appsolulte.smupbackendserver.global.facade

import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class CurrentUser(
    private val userRepository: UserRepository
) {

    fun getPk(): String{
        return SecurityContextHolder.getContext().authentication.credentials.toString()
    }

    fun get(): User{
        return userRepository.findById(getPk()).map{
            it
        }.orElse(null)?: throw UserNotFoundException()
    }

}
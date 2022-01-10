package com.appsolute.soomapi.global.security.service

import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import com.appsolute.soomapi.global.security.data.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

class CustomUserDetailsService(
    private var userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): CustomUserDetails {
        return userRepository.findById(username).map { CustomUserDetails() }
            .orElse(null)?:throw UserNotFoundException()
    }
}

package com.appsolute.soomapi.global.security.service

import com.appsolute.soomapi.global.security.data.CustomUserDetails
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): CustomUserDetails {
        return userRepository.findById(username).map { CustomUserDetails() }
            .orElse(null)?:throw UserNotFoundException()
    }
}

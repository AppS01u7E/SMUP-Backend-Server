package com.appsolulte.smupbackendserver.global.security.service

import com.appsolulte.smupbackendserver.domain.account.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails: UserDetails {

    private val user: User? = null

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val grantedAuthority = GrantedAuthority { user?.getRole()?.name }
        return setOf(grantedAuthority)
    }

    override fun getPassword(): String? {
        return this.user?.password
    }

    override fun getUsername(): String? {
        return this.user?.getEmail()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolulte.smupbackendserver.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}
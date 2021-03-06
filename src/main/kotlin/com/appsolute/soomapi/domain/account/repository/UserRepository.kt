package com.appsolute.soomapi.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, String> {

    fun findByUuidAndGroupInfoContains(uuid: String, groupInfo: GroupInfo): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
}
package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolulte.smupbackendserver.domain.account.entity.GroupInfo
import com.appsolulte.smupbackendserver.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, String> {

    fun findByIdAndGroupInfo(id: String, groupInfo: GroupInfo): Optional<User>
}
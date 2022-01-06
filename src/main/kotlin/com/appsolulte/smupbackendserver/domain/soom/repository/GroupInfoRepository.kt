package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.account.entity.GroupInfo
import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupInfoRepository: JpaRepository<GroupInfo, String> {

    fun findByGroupAndUser(group: Group, user: User): Optional<GroupInfo>
}
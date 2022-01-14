package com.appsolute.soomapi.domain.soom.repository.group


import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupInfoRepository: JpaRepository<GroupInfo, String> {

    fun findByGroupAndUser(group: Group, user: User): Optional<GroupInfo>
}
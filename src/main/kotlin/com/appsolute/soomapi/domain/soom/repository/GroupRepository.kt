package com.appsolute.soomapi.domain.soom.repository

import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupRepository: JpaRepository<Group, String> {

    fun findAllByNameContaining(name: String): List<Group>?
    fun findByIdAndMemberListContains(id: String, member: User): Optional<Group>

}
package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.soom.entity.Group
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, String> {

    fun findAllByNameContaining(name: String): List<Group>?



}
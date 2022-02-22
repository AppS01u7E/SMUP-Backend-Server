package com.appsolute.soomapi.domain.soom.repository.post

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Notice
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NoticeRepository: JpaRepository<Notice, String> {

    fun findByUuidAndWriter(noticeId: String, wirter: User): Optional<Notice>

    fun findAllByGroupAndWriter(group: Soom, writer: User, pageable: Pageable): List<Notice>

}
package com.appsolute.soomapi.domain.soom.repository.post

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NoticeRepository: JpaRepository<Notice, String> {

    fun findByIdAndWriter(noticeId: String, wirter: User): Optional<Notice>
}
package com.appsolute.soomapi.domain.soom.repository

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReplyRepository: JpaRepository<Reply, String> {

    fun findByIdAndWriter(id: String, writer: User): Optional<Reply>
}
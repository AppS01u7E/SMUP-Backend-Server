package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReplyRepository: JpaRepository<Reply, String> {

    fun findByIdAndWriter(id: String, writer: User): Optional<Reply>
}
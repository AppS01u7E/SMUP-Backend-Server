package com.appsolute.soomapi.domain.soom.repository.post

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.entity.Reply
import com.appsolute.soomapi.domain.soom.data.type.ReplyType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReplyRepository: JpaRepository<Reply, String> {

    fun findByUuidAndWriter(id: String, writer: User): Optional<Reply>
    fun findAllByReplyTypeAndWriterAndGroup(replyType: ReplyType, member: User, soom: Soom): List<Reply>
    fun findAllByGroupAndWriter(group: Soom, writer: User, pageable: Pageable): List<Reply>

}
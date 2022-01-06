package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.entity.Group
import com.appsolulte.smupbackendserver.domain.soom.entity.Notice
import com.appsolulte.smupbackendserver.domain.soom.entity.Post
import com.appsolulte.smupbackendserver.domain.soom.entity.PostType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository: JpaRepository<Post, String> {

    fun findAllByGroupAndPostType(group: Group, type: PostType, pageable: Pageable): Page<Post>
    fun findByGroupAndIdAndPostType(group: Group, id: String, type: PostType): Optional<Post>
    fun findAllByReceiverListContainsAndPostType(user: User, pageable: Pageable, postType: PostType): Page<Notice>

}
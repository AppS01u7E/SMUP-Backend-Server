package com.appsolute.soomapi.domain.soom.repository.post

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.entity.Notice
import com.appsolute.soomapi.domain.soom.data.entity.Post
import com.appsolute.soomapi.domain.soom.data.type.PostType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository: JpaRepository<Post, String> {

    fun findAllByGroupAndPostType(group: Group, type: PostType, pageable: Pageable): Page<Post>
    fun findByGroupAndIdAndPostType(group: Group, id: String, type: PostType): Optional<Post>
    fun findAllByReceiverListContainsAndPostType(user: User, pageable: Pageable, postType: PostType): Page<Notice>

}
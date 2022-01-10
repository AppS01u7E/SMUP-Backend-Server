package com.appsolute.soomapi.global.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import kotlin.streams.toList


@Entity
@DiscriminatorValue("NOTICE")
class Notice(
    id: String,
    title: String,
    content: String,
    writer: User,
    group: Group
):  Post(
    id,
    title,
    PostType.NOTICE,
    writer,
    null,
    group
) {

    @Autowired
    @Transient
    private lateinit var current: CurrentUser

    private var content = content

    fun toNoticeResponse(): NoticeResponse{
        return NoticeResponse(
            this.getGroup().toGroupResponse(),
            this.getId(),
            this.getTitle(),
            content,
            this.getWriter().toUserResponse(),
            this.getFileList(),
            this.getLikedMemberList().contains(current.getUser()),
            this.getLike(),
            this.getAimingAtThisPostList().stream().map {
                it.toReplyResponse()
            }.toList()
        )
    }

    fun editNotice(r: PostNoticeRequest): Notice{
        this.content = r.content
        this.setTitle(r.title)
        return this
    }

}
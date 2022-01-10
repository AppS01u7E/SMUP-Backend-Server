package com.appsolulte.smupbackendserver.domain.soom.entity

import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.dto.request.PostNoticeRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.response.NoticeResponse
import com.appsolulte.smupbackendserver.global.facade.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.DiscriminatorValue
import javax.persistence.ElementCollection
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
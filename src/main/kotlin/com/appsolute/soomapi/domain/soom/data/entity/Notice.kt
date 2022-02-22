package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessNoticeResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("NOTICE")
class Notice(
    id: String,
    title: String,
    content: String,
    writer: User,
    soom: Soom
):  Post(
    id,
    title,
    PostType.NOTICE,
    writer,
    null,
    soom
) {

    private var content: String = content


    fun toNoticeResponse(user: User): NoticeResponse{
        return NoticeResponse(
            this.getGroup().toGroupResponse(user),
            this.uuid,
            this.getTitle(),
            content,
            this.getWriter().toUserResponse(),
            this.getFileList(),
            this.getLikedMemberList().contains(user),
            this.getLike(),
            this.getAimingAtThisPostList().stream().map {
                it.toReplyResponse()
            }.toList()
        )
    }

    fun toShortnessNoticeResponse(): ShortnessNoticeResponse{
        return ShortnessNoticeResponse(
            this.uuid,
            this.getTitle(),
            this.content,
            this.getWriter().toShortnessUserResponse(),
            this.getLike(),
            this.getAimingAtThisPostList().size
        )
    }

    fun editNotice(r: PostNoticeRequest): Notice{
        this.content = r.content
        this.setTitle(r.title)
        return this
    }

}
package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.data.type.ReplyType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("REPLY")
class Reply(
    id: String,
    title: String,
    writer: User,
    sendTo: Post,
    group: Group,
    replyType: ReplyType
):Post(
    id,
    title,
    PostType.REPLY,
    writer,
    sendTo,
    group
) {

    private var replyType: ReplyType = replyType

    fun getReplyType(): ReplyType{
        return this.replyType
    }

    fun toReportResponse(): ReplyResponse{
        return ReplyResponse(
            this.getId(),
            this.getTitle(),
            this.getWriter().toUserResponse(),
            this.getSendTo().getId(),
            this.replyType
        )
    }
}
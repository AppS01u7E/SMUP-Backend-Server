package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.response.ReportResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessReplyResponse
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
    soom: Soom,
    replyType: ReplyType
):Post(
    id,
    title,
    PostType.REPLY,
    writer,
    sendTo,
    soom
) {

    private var replyType: ReplyType = replyType

    fun getReplyType(): ReplyType{
        return this.replyType
    }

    fun toReportResponse(): ReportResponse{
        return ReportResponse(
            this.uuid,
            this.getTitle(),
            this.getWriter().toUserResponse(),
            this.getSendTo().uuid,
            this.getFileList()
        )
    }

    fun toShortnessReplyResponse(): ShortnessReplyResponse{
        return ShortnessReplyResponse(
            this.uuid,
            this.getTitle(),
            this.getWriteAt().toLocalDate()
        )
    }

}
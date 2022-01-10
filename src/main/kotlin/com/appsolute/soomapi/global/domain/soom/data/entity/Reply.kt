package com.appsolute.soomapi.global.domain.soom.data.entity

import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.account.data.entity.User

class Reply(
    id: String,
    title: String,
    writer: User,
    sendTo: Post,
    group: Group
):Post(
    id,
    title,
    PostType.REPLY,
    writer,
    sendTo,
    group
) {


}
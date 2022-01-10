package com.appsolulte.smupbackendserver.domain.soom.entity

import com.appsolulte.smupbackendserver.domain.account.entity.User

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
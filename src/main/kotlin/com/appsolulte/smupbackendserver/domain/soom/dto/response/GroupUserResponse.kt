package com.appsolulte.smupbackendserver.domain.soom.dto.response

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.soom.entity.Notice
import com.appsolulte.smupbackendserver.domain.soom.entity.Post
import com.appsolulte.smupbackendserver.domain.soom.entity.Reply
import java.time.LocalDateTime

data class GroupUserResponse(
    var user: UserResponse,
    var joinedAt: LocalDateTime,
    var last10Notice: List<Post>,
    var last10Replies: List<Post>,
    var auth: GroupAuthType

)
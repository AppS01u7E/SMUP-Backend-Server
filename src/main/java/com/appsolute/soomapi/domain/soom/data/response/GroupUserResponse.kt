package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import com.appsolute.soomapi.domain.account.data.deprecated.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.Post
import java.time.LocalDateTime

data class GroupUserResponse(
    var user: UserResponse,
    var joinedAt: LocalDateTime,
    var last10Notice: List<Post>,
    var last10Replies: List<Post>,
    var auth: GroupAuthType

)
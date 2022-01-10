package com.appsolute.soomapi.global.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.deprecated.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.File

data class NoticeResponse (
    var group: GroupResponse,
    var id: String,
    var title: String,
    var content: String,
    var writer: UserResponse,
    var fileList: List<File>,
    var isLiked: Boolean,
    var numOfLike: Int,
    var replies: List<ReplyResponse>

)
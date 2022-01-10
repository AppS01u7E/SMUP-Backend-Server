package com.appsolulte.smupbackendserver.domain.soom.dto.response

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.soom.entity.File

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
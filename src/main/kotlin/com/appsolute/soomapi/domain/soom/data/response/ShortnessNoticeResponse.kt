package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.ShortnessUserResponse
import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.File

data class ShortnessNoticeResponse (
    var id: String,
    var title: String,
    var content: String,
    var writer: ShortnessUserResponse,
    var numOfLike: Int,
    var replyCnt: Int

)
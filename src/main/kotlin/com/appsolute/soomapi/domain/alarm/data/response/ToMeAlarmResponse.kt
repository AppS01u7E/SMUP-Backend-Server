package com.appsolute.soomapi.domain.alarm.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.ShortnessUserResponse
import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse

data class ToMeAlarmResponse (
    val id: String,
    val title: String,
    val message: String,
    val sender: ShortnessUserResponse

)
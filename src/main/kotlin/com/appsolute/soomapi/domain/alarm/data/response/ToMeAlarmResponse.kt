package com.appsolute.soomapi.domain.alarm.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse

data class ToMeAlarmResponse (
    private val title: String,
    private val message: String,
    private val sender: UserResponse
)
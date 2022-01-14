package com.appsolute.soomapi.domain.alarm.service

import com.appsolute.soomapi.domain.alarm.data.response.ToMeAlarmResponse

interface HistoryService {

    fun getMyHistory(): List<ToMeAlarmResponse>


}
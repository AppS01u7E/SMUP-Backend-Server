package com.appsolute.soomapi.domain.alarm.service

import com.appsolute.soomapi.domain.alarm.data.response.ToMeAlarmResponse
import com.appsolute.soomapi.domain.alarm.repository.AlarmRepository
import com.appsolute.soomapi.global.security.CurrentUser

class HistoryServiceImpl(
    private val current: CurrentUser,
    private val alarmRepository: AlarmRepository
): HistoryService {


    override fun getMyHistory(): List<ToMeAlarmResponse> {
        return alarmRepository.findAllByReceiver(current.getUser()).stream().map {
            it.toToMeAlarmResponse()
        }.toList()
    }



}
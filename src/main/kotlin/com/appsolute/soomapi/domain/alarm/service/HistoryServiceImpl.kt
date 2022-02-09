package com.appsolute.soomapi.domain.alarm.service

import com.appsolute.soomapi.domain.alarm.data.response.ToMeAlarmResponse
import com.appsolute.soomapi.domain.alarm.exception.AlarmCannotFoundException
import com.appsolute.soomapi.domain.alarm.repository.AlarmRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.stereotype.Service

@Service
class HistoryServiceImpl(
    private val current: CurrentUser,
    private val alarmRepository: AlarmRepository
): HistoryService {


    override fun getMyHistory(): List<ToMeAlarmResponse> {
        return alarmRepository.findAllByReceiver(current.getUser()).stream().map {
            it.toToMeAlarmResponse()
        }.toList()
    }

    override fun readAlarm(alarmId: String) {
        val alarm = alarmRepository.findById(alarmId).orElse(null)?: throw AlarmCannotFoundException(alarmId)
        alarmRepository.delete(alarm)

    }


}
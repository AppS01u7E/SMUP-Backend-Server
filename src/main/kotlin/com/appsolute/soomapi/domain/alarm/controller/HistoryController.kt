package com.appsolute.soomapi.domain.alarm.controller

import com.appsolute.soomapi.domain.alarm.data.response.ToMeAlarmResponse
import com.appsolute.soomapi.domain.alarm.service.HistoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/call/history")
class HistoryController(
    private val historyService: HistoryService

) {
    //14일 이내의 알림 리스트
    @GetMapping
    fun getMyHistory(): List<ToMeAlarmResponse> {
        return historyService.getMyHistory()
    }

    @DeleteMapping
    fun readAlarm(@RequestParam alarmId: String): ResponseEntity.BodyBuilder {
        historyService.readAlarm(alarmId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
    }


}
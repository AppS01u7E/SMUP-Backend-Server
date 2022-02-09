package com.appsolute.soomapi.domain.alarm.controller

import com.appsolute.soomapi.domain.alarm.service.CallService
import com.appsolute.soomapi.infra.service.fcm.FcmService
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/call")
class CallController(
    private val callService: CallService,
    private val fcmService: FcmService
) {

    @PostMapping("/group/{groupId}/member/{memberId}")
    fun callMember(@PathVariable groupId: String, @PathVariable memberId: String, @RequestBody message: String) {
        return callService.callMember(memberId, groupId, message)
    }

    @PostMapping("/test")
    fun testFcm(@RequestBody token: String): String {
        return fcmService.sendMessage(
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle("202202060346 안지눙")
                        .setBody("Yeah")
                        .setImage("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201901/20/28017477-0365-4a43-b546-008b603da621.jpg")
                        .build()
                )
                .build()
        )
    }



}
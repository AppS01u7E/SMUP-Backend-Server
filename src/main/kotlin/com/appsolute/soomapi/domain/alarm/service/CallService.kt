package com.appsolute.soomapi.domain.alarm.service

import org.springframework.stereotype.Service


@Service
interface CallService {

    //학생 호출
    fun callMember(memberId: String, groupId: String, message: String)

}
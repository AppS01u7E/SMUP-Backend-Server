package com.appsolute.soomapi.domain.alarm.service

import org.springframework.stereotype.Service


@Service
interface CallingService {

    fun callMember(memberId: String, groupId: String, message: String)


}
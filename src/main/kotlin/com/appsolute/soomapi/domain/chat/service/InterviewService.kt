package com.appsolute.soomapi.domain.chat.service

import com.appsolute.soomapi.domain.chat.data.request.ApplyInterviewRequest
import com.appsolute.soomapi.domain.chat.data.request.ConcludeInterviewRequest
import org.springframework.stereotype.Service

interface InterviewService {

    fun applyInterview(group: ApplyInterviewRequest)
    fun concludeInterview(concludeInterviewRequest: ConcludeInterviewRequest)


}
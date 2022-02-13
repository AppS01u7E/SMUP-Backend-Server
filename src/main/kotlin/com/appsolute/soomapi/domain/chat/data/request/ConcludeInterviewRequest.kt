package com.appsolute.soomapi.domain.chat.data.request

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.type.InterviewResultType
import com.appsolute.soomapi.domain.soom.data.entity.Soom

data class ConcludeInterviewRequest (
        val message: String,
        val result: InterviewResultType,
        val group: Soom,
        val user: User
)

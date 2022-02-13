package com.appsolute.soomapi.domain.chat.data.request

import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.fasterxml.jackson.annotation.JsonProperty

data class ApplyInterviewRequest (
    val group: Soom

)
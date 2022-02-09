package com.appsolute.soomapi.domain.chat.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplyInterviewRequest (
    @get:JsonProperty("groupId")
    val groupId: String

)
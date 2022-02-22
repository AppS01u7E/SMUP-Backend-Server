package com.appsolute.soomapi.domain.soom.data.response

import java.time.LocalDate

data class ShortnessReplyResponse(
    val id: String,
    val title: String,
    val writeAt: LocalDate

)

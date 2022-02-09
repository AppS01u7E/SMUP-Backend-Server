package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.dto.response.ShortnessUserResponse

data class ShortnessGroupResponse(
    val id: String,
    val name: String,
    val admin: ShortnessUserResponse,
    val memberCount: Int

)

package com.appsolute.soomapi.domain.soom.data.response

import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType

data class CheckGroupAuthResponse (
    val group: ShortnessGroupResponse,
    val auth: List<GroupAuthType>

)

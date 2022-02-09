package com.appsolute.soomapi.domain.soom.data.request.type

import com.appsolute.soomapi.domain.soom.data.type.GroupType

data class ChangeGroupTypeRequest (
    val groupId: String,
    val type: GroupType
)
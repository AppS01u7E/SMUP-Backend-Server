package com.appsolute.soomapi.global.domain.soom.data.request

import com.appsolute.soomapi.domain.soom.data.type.GroupType

data class GenerateGroupRequest (
    var name: String,
    var description: String,
    var type: GroupType
)
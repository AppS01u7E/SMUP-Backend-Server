package com.appsolulte.smupbackendserver.domain.soom.dto.request

import com.appsolulte.smupbackendserver.domain.soom.entity.GroupType

data class GenerateGroupRequest (
    var name: String,
    var description: String,
    var type: GroupType
)
package com.appsolute.soomapi.domain.soom.data.dto

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Soom

data class GroupAndUserDto (
    var user: User,
    val soom: Soom

)
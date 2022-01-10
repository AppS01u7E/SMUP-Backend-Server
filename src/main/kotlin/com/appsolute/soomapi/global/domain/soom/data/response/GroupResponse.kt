package com.appsolute.soomapi.global.domain.soom.data.response

import com.appsolute.soomapi.domain.account.data.deprecated.TeacherResponse
import com.appsolute.soomapi.domain.account.data.deprecated.UserResponse
import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.soom.data.type.GroupType

data class GroupResponse(
    val id: String,
    val name: String,
    val description: String,
    val type: GroupType,
    val header: User,
    val subHeaderList: List<UserResponse>?,
    val profile: String?,
    val memberCount: Int,
    val memberList: List<UserResponse>,
    val teacher: TeacherResponse?
)
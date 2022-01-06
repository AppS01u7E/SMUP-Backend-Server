package com.appsolulte.smupbackendserver.domain.soom.dto.response

import com.appsolulte.smupbackendserver.domain.account.dto.response.TeacherResponse
import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.entity.GroupType

data class GroupResponse(
    val id: String,
    val name: String,
    val description: String,
    val type: GroupType,
    val header: User,
    val subHeaderList: List<User>?,
    val profile: String?,
    val memberCount: Int,
    val memberList: List<User>,
    val teacher: TeacherResponse?
)
package com.appsolute.soomapi.domain.account.data.dto.inner

import com.appsolute.soomapi.domain.account.data.entity.user.Dept
import com.appsolute.soomapi.domain.account.data.entity.user.Gender
import com.appsolute.soomapi.global.school.data.type.SchoolType

data class MinimStudentDto (
    var user: MinimUserDto,
    var dept: Dept,
    var grade: Int,
    var classNum: Int,
    var number: Int,
    var ent: Int
)
package com.appsolute.soomapi.domain.account.data.dto.inner

import com.appsolute.soomapi.domain.account.data.entity.user.type.Dept

data class MinimStudentDto (
    var user: MinimUserDto,
    var dept: Dept,
    var grade: Int,
    var classNum: Int,
    var number: Int,
    var ent: Int
)
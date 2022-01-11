package com.appsolute.soomapi.domain.account.data.dto.inner

import com.appsolute.soomapi.domain.account.data.entity.user.Gender
import com.appsolute.soomapi.domain.account.data.entity.user.TeacherType
import com.appsolute.soomapi.global.school.data.type.SchoolType

data class MinimTeacherDto (
    var user: MinimUserDto,
    var teacherType: TeacherType,
    var major: String

)
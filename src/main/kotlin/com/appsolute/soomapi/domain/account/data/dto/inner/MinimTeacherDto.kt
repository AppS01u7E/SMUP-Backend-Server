package com.appsolute.soomapi.domain.account.data.dto.inner


import com.appsolute.soomapi.domain.account.data.entity.user.type.TeacherType


data class MinimTeacherDto (
    var user: MinimUserDto,
    var teacherType: TeacherType,
    var major: String

)
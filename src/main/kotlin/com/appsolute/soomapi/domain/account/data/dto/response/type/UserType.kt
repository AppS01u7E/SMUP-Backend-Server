package com.appsolute.soomapi.domain.account.data.dto.response.type

enum class UserType(
    var role: String
) {
    STUDENT("학생"),
    TEACHER("선생")
}
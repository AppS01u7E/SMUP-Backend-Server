package com.appsolute.soomapi.domain.soom.data.type

enum class GroupAuthType (
    private var description: String
) {
    REGISTER_TASK("작업 등록"),
    CALL_GROUP_MEMBER("그룹 멤버 호출"),
    WRITE_NOTICE("공지 작성"),
    NONE("없음")
}
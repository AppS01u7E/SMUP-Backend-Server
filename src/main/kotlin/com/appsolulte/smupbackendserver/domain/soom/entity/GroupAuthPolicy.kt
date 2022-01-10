package com.appsolulte.smupbackendserver.domain.soom.entity

import javax.persistence.Embeddable


enum class GroupAuthPolicy(
    private var description: String
) {
    REGISTER_MEMO("메모 등록"),
    CALL_GROUP_MEMBER("그룹 멤버 호출"),
    WRITE_NOTICE("공지 작성"),
    SEND_FCM("푸시알림 발송")


    //메모 등록
    //그룹 멤버 호출
    //공지 작성
    //푸시알림 발송

}
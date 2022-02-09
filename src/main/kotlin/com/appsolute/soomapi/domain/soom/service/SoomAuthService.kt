package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType

interface SoomAuthService {

    //자신의 권한을 해당 멤버로 넘기기
    fun transferAuthority(groupId: String, userId: String)
    fun removeAuth(groupId: String, userId: String, auth: GroupAuthType)
    fun addAuth(groupId: String, userId: String, auth: GroupAuthType)

}
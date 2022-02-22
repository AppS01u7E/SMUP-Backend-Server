package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.response.CheckGroupAuthResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType

interface SoomAuthService {

    //자신의 권한을 해당 멤버로 넘기기
    fun transferAuthority(groupId: String, userId: String)
    //그룹 권한 제거 => Group Header
    fun removeAuth(groupId: String, userId: String, auth: GroupAuthType)
    //그룹 권한 부여 => Group Header
    fun addAuth(groupId: String, userId: String, auth: GroupAuthType)
    //해당 그룹에 대한 자신의 권한 확인
    fun checkMyAuth(groupId: String): CheckGroupAuthResponse

    fun transferTeacher(groupId: String, teacherId: String)

}
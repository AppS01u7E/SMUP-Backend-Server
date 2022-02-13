package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.response.ProfileResponse
import com.appsolute.soomapi.global.school.data.type.SchoolType
import org.springframework.web.multipart.MultipartFile

interface AccountService {

    fun getMyProfile(): ProfileResponse
    fun getUserProfile(userId: String): ProfileResponse
    fun editMyProfilePhoto(file: MultipartFile)
    fun deleteMyProfilePhoto()
    fun getDepartmentByAccountUUID(accountUUID: String): SchoolType
}
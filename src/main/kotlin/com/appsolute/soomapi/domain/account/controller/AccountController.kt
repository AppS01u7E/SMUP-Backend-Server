package com.appsolute.soomapi.domain.account.controller

import com.appsolute.soomapi.domain.account.data.dto.response.ProfileResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.service.AccountService
import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/account/acc")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/test")
    fun test(): String{
        println("ddsfsa")

        throw UserNotFoundException("dd")
        return "hello"
    }


    // 내 프로필 가져오기
    /* 포함된 정보:
        userId,
        profilePhoto,
        school,
        birth,
        createdAt,
        email,
        name,
        gender
     */
    @GetMapping("/my")
    fun getMyProfile(): ProfileResponse{
        return accountService.getMyProfile()
    }

    //상대 프로필 가져오기
    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: String): ProfileResponse{
        return accountService.getUserProfile(userId)
    }

    //본인 프로필 사진 등록하기
    @PostMapping("/profile")
    fun uploadProfilePhoto(@ModelAttribute profile: MultipartFile){
        return accountService.editMyProfilePhoto(profile)
    }

    //본인 프로필 사진 삭제하기
    @DeleteMapping("/profile")
    fun deleteMyProfilePhoto(){
        return accountService.deleteMyProfilePhoto()
    }



}
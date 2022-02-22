package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.soom.data.response.CheckGroupAuthResponse
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import com.appsolute.soomapi.domain.soom.service.SoomAuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/group")
class SoomAuthController(
    private val soomAuthService: SoomAuthService

) {

    @PostMapping("/auth/transfer/{groupId}/{userId}")
    fun transferAuthority(@PathVariable groupId: String, @PathVariable userId: String) {
        return soomAuthService.transferAuthority(groupId, userId)
    }

    @DeleteMapping("/auth/{groupId}/{userId}")
    fun removeAuth(@PathVariable groupId: String, @PathVariable userId: String, @RequestBody authType: GroupAuthType): ResponseEntity.BodyBuilder{
        soomAuthService.removeAuth(groupId, userId, authType)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/auth/{groupId}/{userId}")
    fun addAuth(@PathVariable groupId: String, @PathVariable userId: String, @RequestBody authType: GroupAuthType): ResponseEntity.BodyBuilder {
        soomAuthService.addAuth(groupId, userId, authType)
        return ResponseEntity.status(HttpStatus.CREATED)
    }

    @GetMapping("/auth/{groupId}")
    fun checkMyAuth(@PathVariable groupId: String): CheckGroupAuthResponse {
        return soomAuthService.checkMyAuth(groupId)
    }

    @PostMapping("/auth/transfer/teacher/{groupId}/{teacherId}")
    fun transferAuthoritiy(@PathVariable groupId: String, @PathVariable teacherId: String) {
        return soomAuthService.transferTeacher(groupId, teacherId)
    }



}
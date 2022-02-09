package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.soom.service.SoomAuthService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/group")
class SoomAuthController(
    private val soomAuthService: SoomAuthService

) {

    @PostMapping("/{groupId}/{userId}")
    fun transferAuthority(@PathVariable groupId: String, @PathVariable userId: String) {
        return soomAuthService.transferAuthority(groupId, userId)
    }


}
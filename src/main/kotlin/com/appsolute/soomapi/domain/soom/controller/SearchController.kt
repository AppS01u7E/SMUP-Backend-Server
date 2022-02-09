package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.service.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/group")
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping("/search")
    fun searchGroupByTitle(@RequestParam query: String): List<GroupResponse> {
        return searchService.searchGroupByTitle(query)
    }

    @GetMapping("/id")
    fun searchGroupById(@RequestParam id: String): GroupResponse{
        return searchService.searchGroupById(id)
    }

    @GetMapping("/list")
    fun getGroupList(@RequestParam idx: Int, @RequestParam size: Int): List<GroupResponse> {
        return searchService.getGroupList(idx, size)
    }


}
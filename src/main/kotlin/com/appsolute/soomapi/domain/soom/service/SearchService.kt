package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import org.springframework.data.domain.PageRequest

interface SearchService {

    fun searchGroupByTitle(query: String): List<GroupResponse>

    fun searchGroupById(id: String): GroupResponse

    fun getGroupList(idx: Int, size: Int): List<GroupResponse>

}
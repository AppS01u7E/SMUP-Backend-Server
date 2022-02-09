package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import org.springframework.data.domain.PageRequest

interface SearchService {
    //그룹 타이틀에 대한 검색
    fun searchGroupByTitle(query: String): List<GroupResponse>
    //그룹 아이디를 통한 검색
    fun searchGroupById(id: String): GroupResponse
    //모든 그룹 리스트 가져오기
    fun getGroupList(idx: Int, size: Int): List<GroupResponse>

}
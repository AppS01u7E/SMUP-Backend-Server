package com.appsolute.soomapi.domain.soom.repository

import com.appsolute.soomapi.domain.account.data.type.SchoolType
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import org.springframework.data.repository.CrudRepository

interface GeneGroupRequestRepository: CrudRepository<GeneGroupRequest, String> {

    fun findAllBySchool(school: SchoolType): List<GeneGroupRequest>
    fun findAllBySchoolAndGroupType(school: SchoolType, groupType: GroupType): List<GeneGroupRequest>

}
package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.account.entity.SchoolType
import com.appsolulte.smupbackendserver.domain.soom.entity.GeneGroupRequest
import com.appsolulte.smupbackendserver.domain.soom.entity.GroupType
import org.springframework.data.repository.CrudRepository

interface GeneGroupRequestRepository: CrudRepository<GeneGroupRequest, String> {

    fun findAllBySchool(school: SchoolType): List<GeneGroupRequest>
    fun findAllBySchoolAndGroupType(school: SchoolType, groupType: GroupType): List<GeneGroupRequest>

}
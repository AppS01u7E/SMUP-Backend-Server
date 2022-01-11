package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType

interface CrudGroupService {


    fun geneGroupRequest(r: GenerateGroupRequest)

    fun getGroupGeneRequestList(): List<GeneGroupRequest>

    fun getGroupGeneRequestListByGroupType(groupType: GroupType): List<GeneGroupRequest>

    fun approveGeneGroupRequest(memberId: String, name: String)

    fun editGroupInfo(groupId: String, r: EditGroupRequest)

    fun changeGroupType(groupId: String, type: GroupType)

    fun setGroupProfile()

    fun deleteGroupRequest(groupId: String)

    fun getDeleteRequestUser(groupId: String): List<UserResponse>

}
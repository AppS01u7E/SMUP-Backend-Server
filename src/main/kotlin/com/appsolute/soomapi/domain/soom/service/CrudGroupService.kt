package com.appsolute.soomapi.domain.soom.service

import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.exception.GeneGroupRequestNotFoundException
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import java.util.*

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
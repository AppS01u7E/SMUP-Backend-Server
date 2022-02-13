package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.request.type.ChangeProfileType
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import org.springframework.web.multipart.MultipartFile

interface CrudGroupService {

    //그룹 생성 요청
    fun geneGroupRequest(r: GenerateGroupRequest)
    //그룹별로 그룹 생성 요청 리스트 확인 => 선생
    fun getGroupGeneRequestListByGroupType(groupType: GroupType?): List<GeneGroupRequest>
    //그룹 생성 요청 승인 => 선생
    fun approveGeneGroupRequest(memberId: String, name: String)

    fun rejectGeneGroupReequest(memberId: String, name: String, ban: Boolean)
    //그룹 설명 수정
    fun editGroupInfo(groupId: String, description: String)
    //그룹 타입 변경
    fun changeGroupType(groupId: String, type: GroupType)
    //그룹 프로필 사진 변경
    fun setGroupProfile(profile: MultipartFile, groupId: String, type: ChangeProfileType)
    //그룹 삭제 요청
    fun deleteGroupRequest(groupId: String)
    //그룹 삭제 요청 취소
    fun cancelDeleteGroupRequest(gropuId: String)

}
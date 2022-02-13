package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.soom.data.request.ApproveGeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.type.ChangeGroupTypeRequest
import com.appsolute.soomapi.domain.soom.data.request.type.ChangeProfileType
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.service.CrudGroupService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotNull


@RestController
@RequestMapping("/api/v1/group")
class CrudGroupController(
    private val crudGroupService: CrudGroupService
) {
    //그룹 생성 요청
    @PostMapping
    fun generateGroupRequest(@RequestBody @NotNull request: GenerateGroupRequest){
        crudGroupService.geneGroupRequest(request)
    }
    //그룹 생성 요청 리스트 확인
    @GetMapping("/request")
    fun getGroupGeneRequestList(@RequestParam type: GroupType): List<GeneGroupRequest>{
        return crudGroupService.getGroupGeneRequestListByGroupType(type)
    }
    //그룹 생성 요청 승인
    @PostMapping("/request")
    fun approveGeneGroupRequest(@RequestBody request: ApproveGeneGroupRequest){
        return crudGroupService.approveGeneGroupRequest(request.memberId, request.name)
    }
    //그룹 설명 수정 요청
    @PostMapping("/profile/info")
    fun editGroupInfo(@RequestParam @NotNull groupId: String, @RequestBody @NotNull description: String){
        return crudGroupService.editGroupInfo(groupId, description)
    }
    //그룹 타입 변경 요청
    @PostMapping("/profile/type")
    fun changeGroupType(@RequestBody request: ChangeGroupTypeRequest){
        return crudGroupService.changeGroupType(request.groupId, request.type)
    }
    //그룹 프로필 사진 변경
    @PostMapping("/profile/photo")
    fun changeProfilePhoto(@ModelAttribute profile: MultipartFile, @RequestParam type: ChangeProfileType, @RequestParam groupId: String){
        return crudGroupService.setGroupProfile(profile, groupId, type)
    }
    //그룹 삭제 요청
    @DeleteMapping
    fun deleteGroupRequest(@RequestParam groupId: String) {
        return crudGroupService.deleteGroupRequest(groupId)
    }


}
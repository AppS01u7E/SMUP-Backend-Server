package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.request.ReceiveJoinRequest
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse
import com.appsolute.soomapi.domain.soom.service.CrudGroupService
import com.appsolute.soomapi.domain.soom.service.ManageMemberService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/group")
class ManageGroupController(
    val manageMemberService: ManageMemberService
) {
    //본인 그룹에 가입 요청한 user List 가져오기
    @GetMapping("/join/list")
    fun getJoinRequestList(@RequestParam groupId: String): List<UserResponse> {
        return manageMemberService.getJoinRequestList(groupId)
    }

    //가입 요청 보내기
    @PutMapping("/join")
    fun sendJoinRequest(@RequestParam groupId: String) {
        return manageMemberService.sendJoinRequest(groupId)
    }
    //가입 요청 받기
    @PostMapping("/join/receive")
    fun receiveJoinRequest(@RequestBody request: ReceiveJoinRequest) {
        return manageMemberService.receiveJoinRequest(request.groupId, request.receiverId)
    }
    //가입 요청 모두 받기
    @PostMapping("/join/receive/all")
    fun receiveJoinRequest(@RequestParam groupId: String) {
        return manageMemberService.receiveEveryJoinRequest(groupId)
    }
    //가입 요청 거절하기
    @PostMapping("/join/reject")
    fun rejectJoinRequest(@RequestParam groupId: String, @RequestParam userId: String) {
        return manageMemberService.rejectJoinRequest(groupId, userId)
    }
    //가입 요청 모두 거절하기
    @PostMapping("/join/reject/all")
    fun rejectJoinRequest(@RequestParam groupId: String) {
        return manageMemberService.rejectAllJoinRequest(groupId)
    }
    //그룹 멤버 리스트 가져오기
    @GetMapping("/member")
    fun getMemberList(@RequestParam groupId: String): List<UserResponse>{
        return manageMemberService.getGroupMemberList(groupId)
    }
    //그룹 멤버 가져오기 (유저의 그룹에 대한 자세한 설정 보여줌)
    @GetMapping("/member/{memberId}")
    fun getGroupMember(@RequestParam groupId: String, @PathVariable memberId: String): GroupUserResponse{
        return manageMemberService.getGroupMember(groupId, memberId)
    }
    //멤버 추방하기
    @DeleteMapping("/kick/{memberId}")
    fun kickMember(@PathVariable memberId: String, @RequestParam groupId: String) {
        return manageMemberService.kickGroupMember(memberId, groupId)
    }
    //그룹 삭제 요청자 리스트 확인
    @GetMapping("/delete/list")
    fun getDeleteRequestUser(@RequestParam groupId: String): List<UserResponse> {
        return manageMemberService.getDeleteRequestUser(groupId)
    }

    @DeleteMapping("/{soomId}")
    fun getOutGroup(@PathVariable soomId: String): List<ShortnessGroupResponse> {
        return manageMemberService.getOutGroup(soomId)
    }

}
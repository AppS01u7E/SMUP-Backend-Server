package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse

interface ManageMemberService {

    //해당 그룹에 요청한 가입 request 가져오기
    fun getJoinRequestList(groupId: String): List<UserResponse>
    //가입 요청 보냈는지 확인
    fun isJoinRequestSent(groupId: String): Boolean
    //가입 요청 보내기
    fun sendJoinRequest(groupId: String)
    //가입 요청 받기 => Group Header
    fun receiveJoinRequest(groupId: String, receiverId: String, message: String)
    //모든 가입 요청 받기 => Group Header
    fun receiveEveryJoinRequest(groupId: String, message: String)
    //가입 요청 거절하기 => Group Header
    fun rejectJoinRequest(groupId: String, studentId: String, message: String)
    //모든 가입 요청 거절하기 => Group Header
    fun rejectAllJoinRequest(groupId: String, message: String)
    //그룹 멤버 리스트 가져오기 => Group Member
    fun getGroupMemberList(groupId: String): List<UserResponse>
    //그룹 멤버 userId로 조회하기 => Group Member
    fun getGroupMember(groupId: String, userId: String): GroupUserResponse
    //멤버 추방하기 => Group Header
    fun kickGroupMember(groupId: String, userId: String)
    //그룹 삭제 요청자 리스트 확인 => Group Member
    fun getDeleteRequestUser(groupId: String): List<UserResponse>
    //그룹 탈퇴 => Group Member
    fun getOutGroup(groupId: String): List<ShortnessGroupResponse>

}
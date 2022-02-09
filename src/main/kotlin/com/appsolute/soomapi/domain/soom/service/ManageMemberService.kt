package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.response.GroupUserResponse
import com.appsolute.soomapi.domain.soom.data.response.ShortnessGroupResponse

interface ManageMemberService {

    //가입 요청 가져오기
    fun getJoinRequestList(groupId: String): List<UserResponse>
    //가입 요청 보냈는지 확인
    fun isJoinRequestSent(groupId: String): Boolean
    //가입 요청 보내기
    fun sendJoinRequest(groupId: String)
    //가입 요청 받기
    fun receiveJoinRequest(groupId: String, receiverId: String)
    //모든 가입 요청 받기
    fun receiveEveryJoinRequest(groupId: String)
    //가입 요청 거절하기
    fun rejectJoinRequest(groupId: String, studentId: String)
    //모든 가입 요청 거절하기
    fun rejectAllJoinRequest(groupId: String)
    //그룹 멤버 리스트 가져오기
    fun getGroupMemberList(groupId: String): List<UserResponse>
    //그룹 멤버 userId로 조회하기
    fun getGroupMember(groupId: String, userId: String): GroupUserResponse
    //멤버 추방하기
    fun kickGroupMember(groupId: String, userId: String)
    //그룹 삭제 요청자 리스트 확인
    fun getDeleteRequestUser(groupId: String): List<UserResponse>

    fun getOutGroup(groupId: String): List<ShortnessGroupResponse>

}
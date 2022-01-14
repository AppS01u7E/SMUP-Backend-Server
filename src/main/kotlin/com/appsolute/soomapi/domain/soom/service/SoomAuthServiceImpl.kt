package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser


class SoomAuthServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val userRepository: UserRepository
    // DI 받는 곳
) {



    fun setSubHeader(groupId: String, memberId: String) {
        var member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        var group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.setSubHeader(member)

        groupRepository.save(group)
    }

    fun removeSubHeader(groupId: String, memberId: String){
        val member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        val group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.dismissalSubHeader(member)

        groupRepository.save(group)
    }


}
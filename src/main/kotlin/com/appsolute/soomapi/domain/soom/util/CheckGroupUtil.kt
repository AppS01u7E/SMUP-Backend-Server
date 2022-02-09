package com.appsolute.soomapi.domain.soom.util

import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.dto.GroupAndUserDto
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.IsAlreadyGroupMmeberException
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import com.appsolute.soomapi.domain.soom.exception.LowerAuthException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.stereotype.Component


@Component
class CheckGroupUtil(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val current: CurrentUser
) {

    fun checkIsGroupMember(groupId: String): GroupAndUserDto {
        return checkIsGroupMember(groupId, null)
    }

    fun checkIsGroupMember(groupId: String, userId: String?): GroupAndUserDto {
        val user = userId?.let { userRepository.findById(it).orElse(null) }
            ?: current.getUser()

        return groupRepository.findById(groupId).orElse(null)?.let{
            if (it.isMember(user)) {
                return GroupAndUserDto(
                    user,
                    it
                )
            } else throw IsNotGroupMemberException(user.id)
        }?: throw GroupCannotFoundException(groupId)
    }

    fun checkIsNotGroupMember(groupId: String): GroupAndUserDto{
        return checkIsGroupMember(groupId, null)
    }

    fun checkIsNotGroupMember(groupId: String, userId: String?): GroupAndUserDto{
        val user = userId?.let { userRepository.findById(it).orElse(null) }
            ?: current.getUser()

        return groupRepository.findById(groupId).orElse(null)?.let{
            if (!it.isMember(user)){
                return GroupAndUserDto(
                    user,
                    it
                )
            } else throw IsAlreadyGroupMmeberException(user.id)
        }?: throw GroupCannotFoundException(groupId)

    }

    fun checkIsGroupHeader(groupId: String): GroupAndUserDto {
        return checkIsGroupHeader(groupId, null)
    }

    fun checkIsGroupHeader(groupId: String, userId: String?): GroupAndUserDto{
        val user = userId?.let { userRepository.findById(it).orElse(null) }
            ?: current.getUser()

        return groupRepository.findById(groupId).orElse(null)?.let{
            if (it.header.equals(current.getUser())) {
                return GroupAndUserDto(
                    user,
                    it
                )
            } else throw IsNotGroupMemberException(user.id)
        }?: throw GroupCannotFoundException(groupId)

    }

}
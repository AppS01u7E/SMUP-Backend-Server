package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import com.appsolute.soomapi.domain.soom.exception.*
import com.appsolute.soomapi.domain.soom.repository.group.GroupInfoRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.util.CheckGroupUtil
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SoomAuthServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val userRepository: UserRepository,
    private val groupInfoRepository: GroupInfoRepository,
    private val check: CheckGroupUtil,
    private val checkGroupUtil: CheckGroupUtil
): SoomAuthService {


    override fun transferAuthority(groupId: String, userId: String){
        val groupAndUserDto = check.checkIsGroupHeader(groupId)
        val group = groupAndUserDto.soom
        val user = groupAndUserDto.user

        val targetGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, user)
            .orElse(null)?: throw GroupCannotFoundException(groupId)
        val actorGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, current.getUser())
            .orElse(null)?: throw GroupCannotFoundException(groupId)

    }

    @Transactional
    override fun removeAuth(groupId: String, userId: String, auth: GroupAuthType) {
        val dto = check.checkIsGroupHeader(groupId)
        val groupInfo = groupInfoRepository.findByGroupAndUser(dto.soom, dto.user).get()
        groupInfo.removeAuth(auth)
    }

    @Transactional
    override fun addAuth(groupId: String, userId: String, auth: GroupAuthType) {
        val dto = check.checkIsGroupHeader(groupId)
        val groupInfo = groupInfoRepository.findByGroupAndUser(dto.soom, dto.user).get()
        groupInfo.addAuth(auth)
    }


}
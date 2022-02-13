package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.entity.user.GroupInfo
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.response.CheckGroupAuthResponse
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


    @Transactional
    override fun transferAuthority(groupId: String, userId: String){
        val groupAndUserDto = check.checkIsGroupHeader(groupId, userId)
        val group = groupAndUserDto.soom
        val user = groupAndUserDto.user
        val current = current.getUser()

        val targetGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, user)
            .orElse(null)?: throw GroupCannotFoundException(groupId)
        val actorGroupInfo: GroupInfo = groupInfoRepository.findByGroupAndUser(group, current)
            .orElse(null)?: throw GroupCannotFoundException(groupId)

        targetGroupInfo.changeAuth(actorGroupInfo)

        if (group.header.equals(current)){
            group.header = user
        }
    }

    @Transactional
    override fun removeAuth(groupId: String, userId: String, auth: GroupAuthType) {
        val receiver = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException(userId)
        val dto = check.checkIsGroupHeader(groupId)
        if (dto.soom.memberList.contains(receiver)) {
            val groupInfo = groupInfoRepository.findById(receiver.uuid + dto.soom.id + "groupInfo").get()
            groupInfo.removeAuth(auth)
        }
    }

    @Transactional
    override fun addAuth(groupId: String, userId: String, auth: GroupAuthType) {
        val receiver = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException(userId)
        val dto = check.checkIsGroupHeader(groupId)
        if (dto.soom.memberList.contains(receiver)) {
            val groupInfo = groupInfoRepository.findById(receiver.uuid + dto.soom.id + "groupInfo").get()
            groupInfo.addAuth(auth)
        }
    }

    @Transactional
    override fun checkMyAuth(groupId: String): CheckGroupAuthResponse {
        val dto = check.checkIsGroupMember(groupId)
        val groupInfo = groupInfoRepository.findById(dto.user.uuid + dto.soom.id + "groupInfo").get()
        return CheckGroupAuthResponse(
            dto.soom.toShortnessGroupResponse(),
            groupInfo.auth
        )
    }


}
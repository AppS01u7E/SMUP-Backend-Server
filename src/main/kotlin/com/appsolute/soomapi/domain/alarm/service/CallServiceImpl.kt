package com.appsolute.soomapi.domain.alarm.service


import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.fcm.FcmService
import org.springframework.stereotype.Service


@Service
class CallServiceImpl(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val fcmService: FcmService,
    private val current: CurrentUser

): CallService {

    override fun callMember(memberId: String, groupId: String, message: String) {
        if (!(groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException(groupId))
            .memberList.contains((userRepository.findById(memberId).orElse(null)?: throw UserNotFoundException(memberId)))){
            throw IsNotGroupMemberException(memberId)
        }
        fcmService.sendTargetMessage(memberId, "${current.getUser().getFirstName()}님이 호출하셨습니다.", message, null)
    }


}
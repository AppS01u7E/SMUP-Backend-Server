package com.appsolute.soomapi.domain.alarm.service


import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.LowerAuthException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.fcm.FcmService
import org.springframework.stereotype.Service


@Service
class CallingServiceImpl(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val fcmService: FcmService,
    private val current: CurrentUser

): CallingService {

    override fun callMember(memberId: String, groupId: String, message: String) {
        if (!(groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException())
            .memberList.contains((userRepository.findById(memberId).orElse(null)?: throw UserNotFoundException()))){
            throw LowerAuthException()
        }
        fcmService.sendTargetMessage(memberId, "${current.getUser().getFirstName()}님이 호출하셨습니다.", message, null)
    }


}
package com.appsolute.soomapi.domain.chat.service

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.domain.chat.data.entity.Message
import com.appsolute.soomapi.domain.chat.data.request.ApplyInterviewRequest
import com.appsolute.soomapi.domain.chat.data.request.ConcludeInterviewRequest
import com.appsolute.soomapi.domain.chat.data.type.ChatRoomType
import com.appsolute.soomapi.domain.chat.data.type.ContentType
import com.appsolute.soomapi.domain.chat.data.type.MessageType
import com.appsolute.soomapi.domain.chat.repository.ChatRoomRepository
import com.appsolute.soomapi.domain.chat.repository.MessageRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.fcm.FcmService
import com.corundumstudio.socketio.SocketIOServer
import org.springframework.stereotype.Service


@Service
class InterviewServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val chatRoomRepository: ChatRoomRepository,
    private val messageRepository: MessageRepository,
    private val server: SocketIOServer,
    private val fcmService: FcmService

): InterviewService {

    override fun applyInterview(request: ApplyInterviewRequest) {
        val group = request.group

        val roomMembers: MutableList<User> = ArrayList<User>()
        roomMembers.addAll(group.memberList)
        roomMembers.add(current.getUser())

        val chatRoomId: String = current.getUser().uuid + group.id
        val chatRoom = ChatRoom(
            chatRoomId,
            group.name + "Interview",
            ChatRoomType.INTERVIEW,
            group.profile,
            roomMembers,
            group.header,
            group
        ) //TODO interview 타입 chatRoom이면 GetMessage 할 때 같은 그룹원일 시 isMine = true로 해야됨

        chatRoomRepository.save(chatRoom)
        messageRepository.save(
            Message(
                current.getUser().getFirstName()+"님께서 면접을 신청하셨습니다.",
                current.getUser(),
                chatRoom,
                MessageType.SYSTEM,
                ContentType.TEXT
            )

        )
        val onlineList = ArrayList<User>()
        onlineList.add(current.getUser())

        fcmService.sendChatRoomAlarm(
            chatRoom, onlineList,
            "새로운 면접 신청자: ${current.getUser().getName()}",
            "${current.getUser().getFirstName()}님께서 면접을 신청하셨어요!"
        )

        return
    }

    override fun concludeInterview(request: ConcludeInterviewRequest) {

        val group = request.group
        val chatRoom = request.group.chattingRoom


        messageRepository.save(
            Message(
                "면접 결과는 "+ request.result + "입니다.",
                current.getUser(),
                chatRoom,
                MessageType.SYSTEM,
                ContentType.TEXT
            )
        )

        chatRoom.makeDone()
        chatRoomRepository.save(chatRoom)

    }

}
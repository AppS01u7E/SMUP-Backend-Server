package com.appsolute.soomapi.infra.service.fcm

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage


interface FcmService {

    fun sendChatRoomAlarm(chatRoom: ChatRoom, onlineUserList: List<User>?, title: String, body: String)

    fun sendTargetMessage(memberId: String, title: String, body: String)

    fun sendTargetMessage(memberId: String, title: String, body: String, image: String?)

    fun sendTopicMessage(topic: String, title: String, body: String)

    fun sendTopicMessage(topic: String, title: String, body: String, image: String?)

    fun sendMessage(message: Message): String

    fun sendMessage(message: MulticastMessage): BatchResponse


}
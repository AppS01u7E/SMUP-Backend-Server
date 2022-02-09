package com.appsolute.soomapi.infra.service.fcm

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.DeviceTokenRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.alarm.data.entity.Alarm
import com.appsolute.soomapi.domain.alarm.repository.AlarmRepository
import com.appsolute.soomapi.domain.chat.data.entity.ChatRoom
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.env.property.FcmProperty
import com.appsolute.soomapi.infra.exception.DeviceTokenNotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class FcmServiceImpl(
    private val fcmProperty: FcmProperty,
    private val alarmRepository: AlarmRepository,
    private val current: CurrentUser,
    private val userRepository: UserRepository,
    private val deviceTokenRepository: DeviceTokenRepository
): FcmService {

    private val objectMapper: ObjectMapper? = null
    private lateinit var instance: FirebaseMessaging

    override fun sendChatRoomAlarm(chatRoom: ChatRoom, onlineUserList: List<User>?, title: String, body: String) {
        val alarmList = chatRoom.alarmReceiverList
        if (onlineUserList != null) {
            alarmList.removeAll(onlineUserList)
        }

        var tokenList: MutableList<String> = ArrayList<String>()
        alarmList.forEach {
            tokenList.addAll(deviceTokenRepository.findById(it.id + "deviceToken").get().getToken())
        }

        val notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()

        val message = MulticastMessage.builder()
            .addAllTokens(tokenList)
            .setNotification(notification)
            .build()
//
//        alarmList.forEach {
//            recordAlarm(title, body, getDataUtil.findUser(client), it)
//        }

        sendMessage(message)
    }

    override fun sendTargetMessage(memberId: String, title: String, body: String) {
        this.sendTargetMessage(memberId, title, body, null)
    }

    override fun sendTargetMessage(memberId: String, title: String, body: String, image: String?) {
        val deviceToken: List<String> = (deviceTokenRepository.findById(memberId + "deviceToken")
            .orElse(null)?: throw DeviceTokenNotFoundException(memberId)).getToken()
        val notification: Notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .setImage(image)
            .build()

        val message = MulticastMessage.builder()
            .addAllTokens(deviceToken)
            .setNotification(notification)
            .build()
        recordAlarm(title, body, current.getUser(),
            userRepository.findById(memberId).orElse(null)
            ?: throw UserNotFoundException(memberId))

        sendMessage(message)
    }

    override fun sendTopicMessage(topic: String, title: String, body: String) {
        this.sendTopicMessage(topic, title, body, null)
    }

    override fun sendTopicMessage(topic: String, title: String, body: String, image: String?) {
        val notice: Notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .setImage(image)
            .build()
        val msg = com.google.firebase.messaging.Message.builder()
            .setToken(topic)
            .setNotification(notice)
            .build()

        sendMessage(msg)
    }

    override fun sendMessage(message: com.google.firebase.messaging.Message): String {
        return this.instance.send(message)
    }

    override fun sendMessage(message: MulticastMessage): BatchResponse {

        return this.instance.sendMulticast(message)
    }

    private fun recordAlarm(title: String, message: String, sender: User, receiver: User){
        alarmRepository.save(
            Alarm(
                title,
                message,
                sender,
                receiver
            )
        )
    }

    @PostConstruct
    override fun init() {
        val googleCredentials: GoogleCredentials = GoogleCredentials.fromStream(ClassPathResource(fcmProperty.configPath).inputStream).createScoped((Arrays.asList(fcmProperty.fireBaseCreateScoped)))

        val secondaryAppConfig: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build()
        val app: FirebaseApp = FirebaseApp.initializeApp(secondaryAppConfig)
        this.instance = FirebaseMessaging.getInstance(app)
    }


}

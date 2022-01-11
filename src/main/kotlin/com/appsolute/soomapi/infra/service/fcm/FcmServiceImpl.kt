package com.appsolute.soomapi.infra.service.fcm

import com.appsolute.soomapi.domain.account.repository.DeviceTokenRepository
import com.appsolute.soomapi.infra.env.property.FcmProperty
import com.appsolute.soomapi.infra.fcm.FcmMessage
import com.appsolute.soomapi.infra.fcm.Message
import com.appsolute.soomapi.infra.exception.DeviceTokenNotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class FcmServiceImpl(
    private val fcmProperty: FcmProperty
): FcmService {
    private lateinit var deviceTokenRepository: DeviceTokenRepository
    private val objectMapper: ObjectMapper? = null
    private lateinit var instance: FirebaseMessaging


    override fun sendTargetMessage(targetToken: String, title: String, body: String) {
        this.sendTargetMessage(targetToken, title, body, null)
    }

    override fun sendTargetMessage(targetToken: String, title: String, body: String, image: String?) {
        val notificatoin: Notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .setImage(image)
            .build()
        val message = com.google.firebase.messaging.Message.builder()
            .setToken(targetToken)
            .setNotification(notificatoin)
            .build()
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

package com.appsolute.soomapi.infra.service.fcm

import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage


interface FcmService {
    fun sendTargetMessage(memberId: String, title: String, body: String)

    fun sendTargetMessage(memberId: String, title: String, body: String, image: String?)

    fun sendTopicMessage(topic: String, title: String, body: String)

    fun sendTopicMessage(topic: String, title: String, body: String, image: String?)

    fun sendMessage(message: Message): String

    fun init()

    fun sendMessage(message: MulticastMessage): BatchResponse


}
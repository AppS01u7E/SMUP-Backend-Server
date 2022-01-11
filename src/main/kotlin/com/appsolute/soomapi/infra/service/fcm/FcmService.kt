package com.appsolute.soomapi.infra.service.fcm


interface FcmService {
    fun sendMessageTo(targetId: String, title: String, body: String): List<String>

    fun sendMessageRangeTo(receiverIds: List<String>, title: String, body: String)
}
package com.appsolute.soomapi.infra.service

import com.appsolulte.smupbackendserver.domain.account.repository.DeviceTokenRepository
import com.appsolute.soomapi.infra.fcm.FcmMessage
import com.appsolute.soomapi.infra.fcm.Message
import com.appsolute.soomapi.infra.fcm.Notification
import com.appsolute.soomapi.infra.exception.DeviceTokenNotFoundException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import java.io.IOException

@Service
//나중에 서비스 추상화 부탁드립니다
class FcmService(
    private val deviceTokenRepository: DeviceTokenRepository,
    private val globalProperties: GlobalProperties
) {

    private val objectMapper: ObjectMapper? = null


    fun sendMessageTo(targetId: String, title: String, body: String): List<String> {
        var tokenList: List<String> = ArrayList<String>()
        val message: MutableList<String> = ArrayList<String>()
        try {
            tokenList = (deviceTokenRepository.findById(targetId).orElse(null)?: throw DeviceTokenNotFoundException()).getToken()
            tokenList.forEach {
                    s ->  message.add(makeMessage(s, title, body)!!)
            }
        } catch (e: NullPointerException) {
            throw DeviceTokenNotFoundException()
        }
        val client = OkHttpClient()
        val responseList: MutableList<String> = ArrayList<String>()

        message.forEach{
                s ->
            run {
                val requestBody = (RequestBody.create("application/json; charset=utf-8".toMediaType(), s))
                val request: Request = Request.Builder()
                    .url(globalProperties.fcm.url)
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build()
                val response = client.newCall(request)
                    .execute()
                responseList.add(response.message)
            }
        }
        return responseList
    }

    @Throws(IOException::class)
    fun sendMessageRangeTo(receiverIds: List<String>, title: String, body: String) {
        for (i in receiverIds) {
            sendMessageTo(i, title, body)
        }
    }

    @Throws(JsonProcessingException::class)
    private fun makeMessage(targetToken: String, title: String, body: String): String? {
        val fcmMessage = FcmMessage(Message(targetToken, Notification(title, body)))

        return objectMapper!!.writeValueAsString(fcmMessage)
    }

    private val accessToken: String
        get() {
            val firebaseConfigPath = globalProperties.fcm.firebaseConfigPath
            val googleCredentials = GoogleCredentials
                .fromStream(ClassPathResource(firebaseConfigPath).inputStream)
                .createScoped(java.util.List.of("https://www.googleapis.com/auth/cloud-platform"))
            googleCredentials.refreshIfExpired()
            return googleCredentials.accessToken.tokenValue
        }


}

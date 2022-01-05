package com.appsolulte.smupbackendserver.global.firebase

data class Notification (
    var title: String,
    var body: String
)

data class Message (
    var token: String,
    var notification: Notification
)

data class FcmMessage (
    var message: Message? = null
)


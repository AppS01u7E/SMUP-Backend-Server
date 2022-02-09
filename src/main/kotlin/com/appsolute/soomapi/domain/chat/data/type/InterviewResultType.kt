package com.appsolute.soomapi.domain.chat.data.type

enum class InterviewResultType(
    private val result: String,
    private val bool: Boolean
) {

    ACCEPT("합격", true),
    REJECT("불합격", false);

    fun getResult(): String{
        return this.result
    }
    fun getBoolean(): Boolean{
        return this.bool
    }
}
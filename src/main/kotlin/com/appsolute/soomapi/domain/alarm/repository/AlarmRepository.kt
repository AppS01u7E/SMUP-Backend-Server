package com.appsolute.soomapi.domain.alarm.repository

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.alarm.data.entity.Alarm
import org.springframework.data.repository.CrudRepository

interface AlarmRepository: CrudRepository<Alarm, String> {

    fun findAllByReceiver(receiver: User): List<Alarm>

}
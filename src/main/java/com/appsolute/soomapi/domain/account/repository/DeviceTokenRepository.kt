package com.appsolute.soomapi.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.DeviceToken
import org.springframework.data.repository.CrudRepository

interface DeviceTokenRepository : CrudRepository<DeviceToken, String> {



}
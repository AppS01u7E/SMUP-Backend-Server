package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolulte.smupbackendserver.domain.account.entity.DeviceToken
import org.springframework.data.repository.CrudRepository

interface DeviceTokenRepository : CrudRepository<DeviceToken, String> {



}
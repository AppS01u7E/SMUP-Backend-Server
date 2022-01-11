package com.appsolute.soomapi.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.token.DeviceToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface DeviceTokenRepository : CrudRepository<DeviceToken, String> {


}
package com.appsolute.soomapi.domain.soom.repository.group

import com.appsolute.soomapi.domain.soom.data.entity.RejectedUser
import org.springframework.data.repository.CrudRepository

interface RejectedUserRepository: CrudRepository<RejectedUser, String> {
}
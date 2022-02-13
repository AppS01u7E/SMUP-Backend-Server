package com.appsolute.soomapi.domain.soom.repository.group

import com.appsolute.soomapi.domain.soom.data.entity.JoinRejectedUser
import org.springframework.data.repository.CrudRepository

interface RejectedUserRepository: CrudRepository<JoinRejectedUser, String> {
}
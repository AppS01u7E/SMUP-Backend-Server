package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.soom.entity.RejectedUser
import org.springframework.data.repository.CrudRepository

interface RejectedUserRepository: CrudRepository<RejectedUser, String> {
}
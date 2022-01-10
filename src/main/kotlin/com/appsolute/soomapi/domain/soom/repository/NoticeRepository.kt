package com.appsolute.soomapi.domain.soom.repository

import com.appsolute.soomapi.domain.soom.data.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, String> {


}
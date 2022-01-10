package com.appsolulte.smupbackendserver.domain.soom.repository

import com.appsolulte.smupbackendserver.domain.soom.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, String> {


}
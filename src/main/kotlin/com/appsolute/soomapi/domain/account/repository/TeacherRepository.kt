package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, String> {
}
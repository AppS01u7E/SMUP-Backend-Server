package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, String> {
}
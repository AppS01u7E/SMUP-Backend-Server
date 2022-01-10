package com.appsolute.soomapi.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, String> {
}
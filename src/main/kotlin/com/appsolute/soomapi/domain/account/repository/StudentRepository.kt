package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.user.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, String> {

}
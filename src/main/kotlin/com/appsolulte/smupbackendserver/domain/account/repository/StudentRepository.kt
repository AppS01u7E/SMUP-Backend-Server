package com.appsolulte.smupbackendserver.domain.account.repository

import com.appsolulte.smupbackendserver.domain.account.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, String> {

}
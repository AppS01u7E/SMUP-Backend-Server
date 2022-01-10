package com.appsolute.soomapi.domain.account.repository

import com.appsolute.soomapi.domain.account.data.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, String> {

}
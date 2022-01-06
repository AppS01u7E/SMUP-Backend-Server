package com.appsolulte.smupbackendserver.global.facade

import com.appsolulte.smupbackendserver.domain.account.entity.Student
import com.appsolulte.smupbackendserver.domain.account.entity.Teacher
import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolulte.smupbackendserver.domain.account.repository.StudentRepository
import com.appsolulte.smupbackendserver.domain.account.repository.TeacherRepository
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class CurrentUser(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository
) {

    fun getPk(): String{
        return SecurityContextHolder.getContext().authentication.credentials.toString()
    }

    fun getUser(): User{
        return userRepository.findById(getPk()).map{
            it
        }.orElse(null)?: throw UserNotFoundException()
    }

    fun getStudent(): Student{
        return studentRepository.findById(getPk()).map {
            it
        }.orElse(null)?: throw UserNotFoundException()
    }

    fun getTeacher(): Teacher {
        return teacherRepository.findById(getPk()).map {
            it
        }.orElse(null)?: throw UserNotFoundException()
    }

}
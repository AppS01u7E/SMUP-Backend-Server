package com.appsolute.soomapi.global.security

import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.TeacherRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
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

    fun getUser(): User {
        return userRepository.findById(getPk()).map{
            it
        }.orElse(null)?: throw UserNotFoundException(getPk())
    }

    fun getStudent(): Student? {
        return studentRepository.findById(getPk()).map {
            it
        }.orElse(null)
    }

    fun getTeacher(): Teacher? {
        return teacherRepository.findById(getPk()).map {
            it
        }.orElse(null)
    }

}
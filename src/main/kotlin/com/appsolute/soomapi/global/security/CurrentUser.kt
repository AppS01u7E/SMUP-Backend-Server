package com.appsolute.soomapi.global.security

import com.appsolute.soomapi.domain.account.data.entity.Student
import com.appsolute.soomapi.domain.account.data.entity.Teacher
import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.TeacherRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

//Data 보단 Service 에 가까운 것 같은데 아예 서비스로 구현하시는건 어떠신가요??
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
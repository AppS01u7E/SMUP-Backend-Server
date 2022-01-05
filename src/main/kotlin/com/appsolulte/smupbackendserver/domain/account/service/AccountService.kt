package com.appsolulte.smupbackendserver.domain.account.service

import com.appsolulte.smupbackendserver.domain.account.dto.request.TeacherSignupRequest
import com.appsolulte.smupbackendserver.domain.account.repository.StudentRepository
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom


@Service
class AccountService(
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository

) {
    private val bCrypt = BCryptPasswordEncoder()


    fun sendEmailCode(email: String){

    }

    fun verifyEmail(code: String, email: String){

    }

    fun teacherVerifyCode(quantity: Int){

    }

    fun teacherSignup(request: TeacherSignupRequest){
        var random = ThreadLocalRandom.current().nextInt(1000000, 9999999).toString()
        userRepository.findById(random).map{

        }

//        request.toTeacher(, bCrypt.encode(request.password))
    }

    fun studentSignup(){


    }








}
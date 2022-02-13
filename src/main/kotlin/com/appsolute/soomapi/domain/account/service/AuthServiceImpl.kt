package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.request.LoginRequest
import com.appsolute.soomapi.domain.account.data.dto.request.SignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.StudentSignupRequest
import com.appsolute.soomapi.domain.account.data.dto.request.TeacherSignupRequest
import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.env.EmailProperty
import com.appsolute.soomapi.domain.account.exception.AlreadyUsedTokenException
import com.appsolute.soomapi.domain.account.exception.IncorrectPasswordException
import com.appsolute.soomapi.domain.account.exception.InvalidTeacherCodeException
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.TeacherRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.util.EmailJwtUtils
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import com.appsolute.soomapi.global.security.util.AccessTokenUtil
import com.appsolute.soomapi.global.security.util.RefreshTokenUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import javax.validation.Valid

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val accessTokenUtil: AccessTokenUtil,
    private val refreshTokenUtil: RefreshTokenUtil,
    private val emailJwtUtils: EmailJwtUtils,
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository,
    private val redisTeacherAuthorizeService: RedisTeacherAuthorizeService

): AuthService {


    override fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email).orElse(null)?: throw UserNotFoundException(request.email)

        if (encoder.encode(request.password).equals(user.password)){
            return TokenResponse(
                accessTokenUtil.encodeToken(user.uuid),
                refreshTokenUtil.encodeToken(user.uuid)
            )
        } else throw IncorrectPasswordException(request.password)
    }



    override fun signup(request: SignupRequest): () -> TokenResponse {
        var randomId: String = ThreadLocalRandom.current().nextInt(10000000, 99999999).toString() //유저 저장시 사용할 id 생성
        if (userRepository.existsById(randomId)) randomId = ThreadLocalRandom.current().nextInt(10000000, 99999999).toString() // 검증

        return {
            if (request is StudentSignupRequest) signup(request, randomId)
            else signup(request as TeacherSignupRequest, randomId)
        }
    }



    private fun signup(@Valid request: StudentSignupRequest, randomId: String): TokenResponse{
        val email = emailCheck(request.email)
        val student: Student = request.toStudent(randomId, encoder.encode(request.password))
        studentRepository.save(student)

        return makeTokenResponse(student.uuid)
    }



    private fun signup(@Valid request: TeacherSignupRequest, randomId: String): TokenResponse{
        val email = emailCheck(request.email)
        if (!redisTeacherAuthorizeService.checkTeacherCode(request.teacherCode)) throw InvalidTeacherCodeException(request.teacherCode)

        val teacher: Teacher = request.toTeacher(randomId, encoder.encode(request.password))
        teacherRepository.save(teacher)

        return makeTokenResponse(teacher.uuid)
    }



    private fun emailCheck(emailToken: String): String {
        val email = emailJwtUtils.decodeToken(emailToken)
        if (userRepository.existsByEmail(email)) throw AlreadyUsedTokenException(email)
        return email
    }

    private fun makeTokenResponse(data: String): TokenResponse {
        return TokenResponse(
            accessTokenUtil.encodeToken(data),
            refreshTokenUtil.encodeToken(data)
        )
    }

}
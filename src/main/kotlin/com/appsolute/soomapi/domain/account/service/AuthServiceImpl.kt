package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.request.*
import com.appsolute.soomapi.domain.account.data.entity.token.DeviceToken
import com.appsolute.soomapi.domain.account.data.entity.user.Student
import com.appsolute.soomapi.domain.account.data.entity.user.Teacher
import com.appsolute.soomapi.domain.account.exception.*
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.TeacherRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.account.util.EmailJwtUtils
import com.appsolute.soomapi.global.school.data.type.SchoolType
import com.appsolute.soomapi.global.security.data.response.TokenResponse
import com.appsolute.soomapi.global.security.util.AccessTokenUtil
import com.appsolute.soomapi.global.security.util.RefreshTokenUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import javax.transaction.Transactional
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

    @Transactional
    override fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email).orElse(null)?: throw UserNotFoundException(request.email)

        if (encoder.matches(request.password, user.password)){
            request.deviceToken?.let {
                user.addToken(it)
            }
            return TokenResponse(
                accessTokenUtil.encodeToken(user.uuid),
                refreshTokenUtil.encodeToken(user.uuid)
            )
        } else throw IncorrectPasswordException(request.password)
    }

    override fun reissue(request: RefreshTokenReissueRequest): TokenResponse {
        val userPk = refreshTokenUtil.decodeToken(request.refreshToken)
        val user = userRepository.findById(userPk).orElse(null)?: throw UserNotFoundException(userPk)
        return makeTokenResponse(userPk)
    }


    override fun studentSignup(@Valid request: StudentSignupRequest): TokenResponse{
        val randomId = randomId()
        val email = emailCheck(request.emailToken)
        val student: Student = request.toStudent(randomId, email, encoder.encode(request.password), getSchool(email))
        studentRepository.save(student)

        return makeTokenResponse(student.uuid)
    }



    override fun teacherSignup(@Valid request: TeacherSignupRequest): TokenResponse{
        val randomId = randomId()
        val email = emailCheck(request.emailToken)
        if (!redisTeacherAuthorizeService.checkTeacherCode(request.teacherCode)) throw InvalidTeacherCodeException(request.teacherCode)
        redisTeacherAuthorizeService.deleteTeacherCode(request.teacherCode)
        val teacher: Teacher = request.toTeacher(randomId, email, encoder.encode(request.password), getSchool(email))

        teacherRepository.save(teacher)

        return makeTokenResponse(teacher.uuid)
    }

    private fun randomId(): String{
        var randomId: String = ThreadLocalRandom.current().nextInt(10000000, 99999999).toString() //유저 저장시 사용할 id 생성
        if (userRepository.existsById(randomId)) randomId = ThreadLocalRandom.current().nextInt(10000000, 99999999).toString() // 검증

        return randomId
    }


    private fun getSchool(email: String): SchoolType {
        for (allow in SchoolType.values()) {
            if (allow.checkPolicy(email)) return allow
        }
        throw InvalidEmailException(email)
    }


    private fun emailCheck(emailToken: String): String {
        val email = emailJwtUtils.decodeToken(emailToken)
        println(email)
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

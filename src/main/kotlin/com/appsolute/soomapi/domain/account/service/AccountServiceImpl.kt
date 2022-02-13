package com.appsolute.soomapi.domain.account.service

import com.appsolute.soomapi.domain.account.data.dto.response.ProfileResponse
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.StudentRepository
import com.appsolute.soomapi.domain.account.repository.TeacherRepository
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.global.school.data.type.SchoolType
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class AccountServiceImpl(
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository,
    private val current: CurrentUser,
    private val s3Util: S3Util,
    private val userRepository: UserRepository
): AccountService {


    override fun getMyProfile(): ProfileResponse {
        return current.getStudent()?.toStudentResponse() ?: current.getTeacher()?.toTeacherResponse() ?: throw UserNotFoundException(current.getPk())

    }

    override fun getUserProfile(userId: String): ProfileResponse {
        return studentRepository.findById(userId).orElse(null)?.toStudentResponse()
            ?: teacherRepository.findById(userId).orElse(null)?.toTeacherResponse()
            ?: throw UserNotFoundException(userId)
    }

    override fun editMyProfilePhoto(file: MultipartFile){
        val fileKey = s3Util.upload(file, "user/${current.getPk()}")
        val user = current.getUser()
        user.settingProfile(fileKey)
        userRepository.save(user)
    }


    override fun deleteMyProfilePhoto() {
        val user = current.getUser()
        user.settingProfile(null)
        userRepository.save(user)
    }

    override fun getDepartmentByAccountUUID(accountUUID: String): SchoolType {
        val user = userRepository.findById(accountUUID).orElse(null)?: throw UserNotFoundException(accountUUID)

        return user.school
    }


}
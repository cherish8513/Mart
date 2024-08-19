package com.example.api.mart.service.impl

import com.example.api.mart.dto.*
import com.example.api.mart.repository.CountryRepository
import com.example.api.mart.repository.UserRepository
import com.example.api.mart.service.TransactionalLockHelper
import com.example.api.mart.service.UserService
import com.example.api.static.exception.CustomExceptionType
import com.example.api.static.exception.CustomExceptionType.NOT_FOUND_USER
import com.example.api.util.assertNotNull
import com.example.api.util.isNotNull
import com.example.domain.mart.TbUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(rollbackFor = [Exception::class])
@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository,
    private val transactionalLockHelper: TransactionalLockHelper
) : UserService {
    override fun addUser(userPostDto: UserPostDto): Long {
        countryRepository.findByIdOrNull(userPostDto.countryId).assertNotNull()
        return userRepository.save(
            TbUser(
                countryId = userPostDto.countryId,
                name = userPostDto.name,
                registrationNumber = sequenceRegistrationNumberGenerator(),
                phoneNumber = userPostDto.phoneNumber,
                birthYmd = userPostDto.birthYmd,
                genderCode = userPostDto.genderCode
            )
        ).userId.assertNotNull()
    }

    override fun updateUserProfile(userPutDto: UserPutDto) {
        val foundUser = userRepository.findByIdOrNull(userPutDto.userId).assertNotNull(NOT_FOUND_USER)
        foundUser.changeUserInfo(
            name = userPutDto.name,
            genderCode = userPutDto.genderCode,
            phoneNumber = userPutDto.phoneNumber
        )
    }

    override fun getUser(userGetDto: UserGetDto): UserDto {
        val foundUser = userRepository.findByCountryIdAndUserId(userGetDto.countryId, userGetDto.userId.assertNotNull())
            .assertNotNull(NOT_FOUND_USER)

        return UserDto(
            userId = foundUser.userId.assertNotNull(),
            name = foundUser.name,
            genderCode = foundUser.genderCode,
            registrationNumber = foundUser.registrationNumber,
            birthday = foundUser.birthYmd,
            phoneNumber = foundUser.phoneNumber,
            point = foundUser.point
        )
    }

    private fun sequenceRegistrationNumberGenerator(): Int {
        return if (transactionalLockHelper.lockAndRegisterUnlock(REGISTRATION_NUMBER_LOCK_ID, 20)) {
            userRepository.findLastRegistrationNumber()?.let { it + 1 } ?: 1
        } else {
            throw CustomExceptionType.RETRY_REQUEST.toException()
        }
    }

    companion object {
        const val REGISTRATION_NUMBER_LOCK_ID = "USER:REGISTRATION:LOCK"
    }
}
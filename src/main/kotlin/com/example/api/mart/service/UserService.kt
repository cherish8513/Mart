package com.example.api.mart.service

import com.example.api.mart.dto.*

interface UserService {
    fun addUser(userPostDto: UserPostDto): Long
    fun updateUserProfile(userPutDto: UserPutDto)
    fun getUser(userGetDto: UserGetDto): UserDto
}
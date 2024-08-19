package com.example.api.mart.controller

import com.example.api.mart.dto.UserDto
import com.example.api.mart.dto.UserGetDto
import com.example.api.mart.dto.UserPostDto
import com.example.api.mart.dto.UserPutDto
import com.example.api.mart.service.UserService
import com.example.api.util.ResponseDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun saveUser(@Valid @RequestBody userPostDto: UserPostDto): ResponseDto<Long> {
        return ResponseDto(userService.addUser(userPostDto))
    }

    @PutMapping
    fun updateUser(@Valid @RequestBody userPutDto: UserPutDto): ResponseDto<Unit> {
        return ResponseDto(userService.updateUserProfile(userPutDto))
    }

    @GetMapping
    fun getUser(userGetDto: UserGetDto): ResponseDto<UserDto> {
        return ResponseDto(userService.getUser(userGetDto))
    }
}
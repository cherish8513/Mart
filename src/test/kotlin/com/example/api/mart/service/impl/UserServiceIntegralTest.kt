package com.example.api.mart.service.impl

import com.example.api.mart.dto.UserPostDto
import com.example.api.mart.service.UserService
import com.example.api.static.exception.CustomException
import com.example.domain.GenderCode
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import shouldBe
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

@SpringBootTest
class UserServiceIntegralTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun `success to add user in multi thread`() {
        // given
        val createCount = 100
        val executor = Executors.newFixedThreadPool(10)
        var successCount = 0
        var failCount = 0

        // when
        val tasks = (1..createCount).map { index ->
            Callable {
                userService.addUser(userPostDtoGenerator("손님$index"))
            }
        }

        executor.invokeAll(tasks).forEach {
            try {
                it.get()
                successCount++
            } catch (e: ExecutionException) {
                if(e.cause is CustomException) {
                    failCount++
                }
            }
        }
        executor.shutdown()

        // then
        successCount shouldBe createCount
    }

    private fun userPostDtoGenerator(name: String): UserPostDto {
        return UserPostDto(
            countryId = 1L,
            receptionDate = LocalDateTime.now().toString(),
            name = name,
            genderCode = GenderCode.MALE,
            birthYmd = "19960101"
        )
    }
}
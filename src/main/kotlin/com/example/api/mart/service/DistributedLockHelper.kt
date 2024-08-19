package com.example.api.mart.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class DistributedLockHelper(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun lock(key: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent(key, "", Duration.ofSeconds(10)) == true
    }

    fun unlock(key: String) {
        redisTemplate.delete(key)
    }
}
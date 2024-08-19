package com.example.api.static.config

import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy

@Configuration
class EmbeddedRedisConfig {

    private lateinit var redisServer: RedisServer

    @PostConstruct
    fun startRedis() {
        redisServer = RedisServer(6379)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}
package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MartApplication

fun main(args: Array<String>) {
    runApplication<MartApplication>(*args)
}

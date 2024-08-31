package com.example.api.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LoggerUtil {
    inline fun <reified T : Any> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)

    fun getLogger(name: String): Logger = LoggerFactory.getLogger(name)
}
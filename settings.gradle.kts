rootProject.name = "example"

pluginManagement {
    val springBootVersion: String by settings // from gradle.properties
    val kotlinVersion: String by settings // from gradle.properties

    plugins {
        // Spring Boot for Kotlin
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        id("org.springframework.boot") version springBootVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        // Etc
        kotlin("plugin.jpa") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.noarg") version kotlinVersion
    }
}
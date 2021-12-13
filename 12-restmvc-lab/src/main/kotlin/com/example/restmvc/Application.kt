package com.example.restmvc

import course.kotlin.spring.properties.BlogProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

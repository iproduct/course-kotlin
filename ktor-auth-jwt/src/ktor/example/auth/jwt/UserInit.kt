package ktor.example.auth.jwt

import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication
import ktor.example.auth.jwt.User

val ApplicationCall.user get() = authentication.principal<User>()

val sampleUser = User(1, "Test", setOf(Role.USER, Role.ADMIN))

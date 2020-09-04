package ktor.example.auth.jwt

import io.ktor.auth.*

data class User(
        val id: Int,
        val name: String,
        val roles: Set<Role>
) : Principal

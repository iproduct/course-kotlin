package ktor.example.auth.jwt

import io.ktor.auth.*
import ktor.example.auth.jwt.User

interface UserRepo {

    fun findUserById(id: Int): User

    fun findUserByCredentials(credential: UserPasswordCredential): User

}

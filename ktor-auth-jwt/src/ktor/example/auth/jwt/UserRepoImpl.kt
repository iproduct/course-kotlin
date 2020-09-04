package ktor.example.auth.jwt

import io.ktor.auth.*
import ktor.example.auth.jwt.User
import ktor.example.auth.jwt.UserRepo

class UserRepoImpl : UserRepo {

    override fun findUserById(id: Int): User = users.getValue(id)

    override fun findUserByCredentials(credential: UserPasswordCredential): User = sampleUser

    private val users = listOf(sampleUser).associateBy(User::id)

}

package ktor.example.auth.jwt

import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val Logger: Logger = LoggerFactory.getLogger("ktor.example.auth.jwt")

fun Application.module() {
    install(CallLogging)
    install(ContentNegotiation) { gson { } }

    val userSource: UserRepo = UserRepoImpl()
    install(Authentication) {
        /**
         * Setup the JWT authentication to be used in [Routing].
         * If the token is valid, the corresponding [User] is fetched from the database.
         * The [User] can then be accessed in each [ApplicationCall].
         */
        jwt {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                val id = it.payload.getClaim("id").asInt()
                Logger.debug("ID: $id")
                id?.let(userSource::findUserById)
            }
        }
    }

    install(Routing) {

        /**
         * A public login [Route] used to obtain JWTs
         */
        post("login") {
            val credentials = call.receive<UserPasswordCredential>()
            val user = userSource.findUserByCredentials(credentials)
            val token = JwtConfig.makeToken(user)
            call.application.environment.log.info("User logged successfully and receiving JWT as: $user")
            call.respondText(token)
        }

        /**
         * All [Route]s in the authentication block are secured.
         */
        authenticate {
            route("secret-roles") {

                get {
                    val user = call.user!!
                    call.respond(user.roles)
                }

            }
        }

        /**
         * Routes with optional authentication
         */
        authenticate(optional = true) {
            get("optional-auth") {
                val user = call.user
                val response = if (user != null) "authenticated as $user" else "auth is optional here ..."
                call.respond(response)
            }
        }
    }
}

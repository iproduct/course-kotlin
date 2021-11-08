package course.plugins

import io.ktor.http.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureHTTP() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    install(CallLogging) {
//        format { call ->
//            val status = call.response.status()
//            val httpMethod = call.request.httpMethod.value
//            val userAgent = call.request.headers["User-Agent"]
//            "Status: $status, HTTP method: $httpMethod, User agent: $userAgent"
//        }
    }
}

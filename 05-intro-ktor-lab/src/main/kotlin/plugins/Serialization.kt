package course.kotlin.plugins

import io.ktor.jackson.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(JavaTimeModule())  // support java.time.* types
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        }
    }

    routing {
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

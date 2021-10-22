package course.kotlin.plugins

import course.kotlin.model.Product
import course.kotlin.productRepo
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/api/products") {
            call.respond(application.productRepo.findAll())
        }
    }
}

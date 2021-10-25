package course.kotlin.plugins

import course.kotlin.model.Identifiable
import course.kotlin.model.Product
import course.kotlin.productRepo
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*


private suspend inline fun <K, reified T : Identifiable<K>> ApplicationResponse.created(entity: T) {
    call.response.status(HttpStatusCode.Created)
    call.response.header(HttpHeaders.Location, "${call.request.uri}/${entity.id}")
    call.respond(entity)
}

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/api/products") {
            call.respond(application.productRepo.findAll())
        }
        post("/api/products") {
            val newProduct = call.receive(Product::class)
            val created = application.productRepo.create(newProduct)
//            call.response.headers.append(HttpHeaders.Location, "/api/products/${created.id}")
            call.response.created(created)
        }
    }
}


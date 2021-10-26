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
//            .context.request.path
            call.respond(productRepo.findAll())
        }
        get("/api/services") {
            call.respond(productRepo.findAll())
        }
        get("/api/devices") {
            call.respond(productRepo.findAll())
        }
        post("/api/products") {
            val newProduct = call.receive(Product::class)
            val created = productRepo.create(newProduct)
//            call.response.headers.append(HttpHeaders.Location, "/api/products/${created.id}")
            call.response.created(created)
        }
    }
}


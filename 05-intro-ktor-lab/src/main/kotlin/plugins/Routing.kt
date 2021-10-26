package course.kotlin.plugins

import course.kotlin.model.ProductData
import course.kotlin.productRepo
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {

    routing {
        get("/api/products") {
            call.respond(productRepo.values)
        }
        get("/api/services") {
            call.respond(productRepo.values)
        }
        get("/api/accessoaries") {
            call.respond(productRepo.values)
        }
        get("/api/products/{id}") {
            try {
                val id = call.parameters["id"]?.toInt()
                val product = productRepo.get(id)
                if (product != null) {
                    call.respond(product)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound, """
                    {"error" : "Product with id: ${id} not found"}
                """.trimIndent()
                    )
                }
            } catch (ex: NumberFormatException ) {
                call.respond(
                    HttpStatusCode.BadRequest, """
                    {"error" : "Invalid product ID: ${call.parameters["id"]}"}
                """.trimIndent()
                )
            }
        }
        post("/api/products") {
            errorAware {
                val productData = call.receive(ProductData::class)
                val product =  productRepo.addProduct(productData)
                call.respond(HttpStatusCode.Created, product)
            }
        }
    }
}

private suspend fun<R> PipelineContext<*, ApplicationCall>. errorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        call.respond(
            HttpStatusCode.InternalServerError, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    }
}

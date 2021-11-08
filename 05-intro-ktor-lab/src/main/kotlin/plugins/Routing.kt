package course.kotlin.plugins

import course.kotlin.exceptions.EntityNotFoundException
import course.kotlin.exceptions.InvalidClientDataException
import course.kotlin.model.Product
import course.kotlin.productRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import java.lang.NumberFormatException

fun Application.configureRouting() {

    routing {
        get("/api/products") {
            val resp = application.productRepo.findAll()
            call.respond(resp)
        }
        get("/api/services") {
            call.respond(application.productRepo.findAll())
        }
        get("/api/accessoaries") {
            call.respond(application.productRepo.findAll())
        }
        get("/api/products/{id}") {
            serverErrorAware {
                clientRequestErrorAware {
                    val id = call.parameters["id"]?.toInt()
                    val product = application.productRepo.findById(id!!)
                        ?: throw EntityNotFoundException("Product with id: ${id} not found")
                    call.respond(product)
                }
            }
        }
        post("/api/products") {
            serverErrorAware {
                val productData = call.receive(Product::class)
                val product = application.productRepo.create(productData)
                call.response.header("Location", "${call.request.path()}/${product.id}")
                call.respond(HttpStatusCode.Created, product)
            }
        }
        put("/api/products/{id}") {
            val productData = call.receive(Product::class)
            TODO()
        }
        delete("/api/products/{id}") {
            TODO()
        }
    }
}

private suspend fun <R> PipelineContext<*, ApplicationCall>.serverErrorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
//        if (call.response.status() == null) {
        call.respond(
            HttpStatusCode.InternalServerError, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    }
}

private suspend fun <R> PipelineContext<*, ApplicationCall>.clientRequestErrorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: EntityNotFoundException) {
        call.respond(
            HttpStatusCode.NotFound, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    } catch (e: NumberFormatException) {
        call.respond(
            HttpStatusCode.BadRequest, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    } catch (e: InvalidClientDataException) {
        call.respond(
            HttpStatusCode.BadRequest, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    }
}

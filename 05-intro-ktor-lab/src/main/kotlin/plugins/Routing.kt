package course.kotlin.plugins

import course.kotlin.exceptions.EntityNotFoundException
import course.kotlin.exceptions.InvalidClientDataException
import course.kotlin.model.Product
import course.kotlin.productRepo
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import java.lang.NullPointerException
import java.lang.NumberFormatException

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondHtml {
                body {
                    this@respondHtml.head {
                        title() {text("This should not be possible") }
                    }
                    h2 {
                        a(href = "/api/products") { + "Go to /products" }
                    }
                }
            }
        }
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
            serverErrorAware {
                clientRequestErrorAware {
                    val id = call.parameters["id"]?.toInt()
                    val productData = call.receive(Product::class)
                    if (id == null || id != productData.id) throw InvalidClientDataException("ID in URL or body not valid")
                    application.productRepo.findById(id!!)
                        ?: throw EntityNotFoundException("Product with id: ${id} not found")
                    if (productData.price < 0) throw InvalidClientDataException("The price should be positive number")
                    val product = application.productRepo.update(productData)
                    call.respond(product)
                }
            }
        }
        delete("/api/products/{id}") {
            serverErrorAware {
                clientRequestErrorAware {
                    val id = call.parameters["id"]?.toInt() ?:
                        throw EntityNotFoundException("Invalid entity ID")
                    val deleted = application.productRepo.deleteById(id)?:
                        throw EntityNotFoundException("Entity with ID=${id} not found")
                    call.respond(deleted)
                }
            }
        }
    }
}

private suspend fun <R> PipelineContext<*, ApplicationCall>.serverErrorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        application.log.error("Call error", e)
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
       application.log.error("Call error", e)
        call.respond(
            HttpStatusCode.NotFound, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    } catch (e: NullPointerException) {
        application.log.error("Call error", e)
        call.respond(
            HttpStatusCode.NotFound, """
                    {"error" : "Entity not found" }
                """.trimIndent()
        )
        null
    } catch (e: NumberFormatException) {
        application.log.error("Call error", e)
        call.respond(
            HttpStatusCode.BadRequest, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    } catch (e: InvalidClientDataException) {
        application.log.error("Call error", e)
        call.respond(
            HttpStatusCode.BadRequest, """
                    {"error" : ${e.message}}
                """.trimIndent()
        )
        null
    }
}

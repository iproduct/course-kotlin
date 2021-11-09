package course.kotlin.plugins

import course.kotlin.exception.NonExistingEntityException
import course.kotlin.model.Identifiable
import course.kotlin.model.Product
import course.kotlin.productRepo
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import java.lang.NullPointerException
import java.lang.NumberFormatException


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
        get("/api/products/{id}") {
            serverErrorAware {
                clientErrorAware {
                    val id = call.parameters["id"]?.toInt()
                    val product = productRepo.findById(id!!)
                            ?: throw NonExistingEntityException("Product with ID=$id does not exist")
                    call.respond(product)
                }
            }
        }
        post("/api/products") {
            serverErrorAware {
                val newProduct = call.receive(Product::class)
                val created = productRepo.create(newProduct)
                call.response.created(created)
            }
        }
    }
}

//Extension functions
/** Respond with 201 Crated and Location header + entity in the body **/
private suspend inline fun <K, reified T : Identifiable<K>> ApplicationResponse.created(entity: T) {
    call.response.status(HttpStatusCode.Created)
    call.response.header(HttpHeaders.Location, "http://${call.request.header(HttpHeaders.Host)}${call.request.uri}/${entity.id}")
    call.respond(entity)
}

/** Handle server errors - catch all clause - should be the outer extension function **/
private suspend fun <Result> PipelineContext<*, ApplicationCall>.serverErrorAware(block: suspend ()-> Result): Result? =
    try {
       block()
    } catch (e: Exception) {
        application.log.error("Server error", e)
        call.respond(HttpStatusCode.InternalServerError, """
            {"error" : ${e.message}}
        """.trimIndent())
        null
    }


/** Handle client errors - catch NumberFormatException, InvalidClientDataException, NonExistingEntityException **/
private suspend fun <Result> PipelineContext<*, ApplicationCall>.clientErrorAware(block: suspend ()-> Result): Result? =
    try {
       block()
    } catch (e: NumberFormatException) {
        respondWithError(HttpStatusCode.BadRequest, "Invalid ID format", e)
    } catch (e: NullPointerException) {
        respondWithError(HttpStatusCode.BadRequest, "ID is missing", e)
    } catch (e: NonExistingEntityException) {
        respondWithError(HttpStatusCode.NotFound, "Entity not found", e)
    }

private suspend fun PipelineContext<*, ApplicationCall>.respondWithError(status:HttpStatusCode, message: String, ex: Throwable): Nothing? {
    application.log.error(message, ex)
    call.respond(
        HttpStatusCode.BadRequest, """
            {"error" : ${message}: ${ex.message}}
        """.trimIndent()
    )
    return null
}



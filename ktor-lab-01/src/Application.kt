package com.example

import com.codahale.metrics.JmxReporter
import com.codahale.metrics.Slf4jReporter
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.metrics.dropwizard.DropwizardMetrics
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import java.text.DateFormat
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

data class Product(val name: String, val price: Double, var id: Int)

object Repo : ConcurrentHashMap<Int, Product>() {
    private val idCounter = AtomicInteger()
    fun addProduct(product: Product) {
        product.id = idCounter.incrementAndGet()
        put(product.id, product)
    }
}


fun main() {
    val server =
        embeddedServer(Netty, port = 8080, watchPaths = listOf("build/classes"), module = Application::mymodule)
            .start(wait = true)
}


fun Application.mymodule() {
    install(DefaultHeaders)
    install(CORS) {
        maxAgeInSeconds = Duration.ofDays(1).seconds
    }
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    install(DropwizardMetrics) {
        JmxReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start()
        Slf4jReporter.forRegistry(registry)
            .outputTo(log)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start(10, TimeUnit.SECONDS)
    }

    routing {
        get("/products") {
            call.respond(Repo.values)
        }
        get("/products/{id}") {
            try {
                val item = Repo.get(call.parameters["id"]?.toInt())
                if (item == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        """{"error":"Product not found with id = ${call.parameters["id"]}"}"""
                    )
                } else {
                    call.respond(item)
                }
            } catch (ex: NumberFormatException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    """{"error":"Invalid product id: ${call.parameters["id"]}"}"""
                )
            }
        }
        post("/products") {
            errorAware {
            val product: Product = call.receive<Product>(Product::class)
            println("Received Post Request: $product")
            Repo.addProduct(product)
            call.respond(HttpStatusCode.Created, product)
            }
        }
    }
}

private suspend fun <R> PipelineContext<*, ApplicationCall>.errorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        call.respondText(
            """{"error":"$e"}""",
            ContentType.parse("application/json"),
            HttpStatusCode.InternalServerError
        )
        null
    }
}


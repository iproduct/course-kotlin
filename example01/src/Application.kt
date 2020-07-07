package org.iproduct.ktor

import com.codahale.metrics.JmxReporter
import com.codahale.metrics.Slf4jReporter
import io.ktor.application.*
import io.ktor.client.HttpClient
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.metrics.dropwizard.DropwizardMetrics
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import kotlinx.css.*
import kotlinx.html.*
import java.lang.NumberFormatException
import java.text.DateFormat
import java.time.Duration
import java.util.*
import java.util.UUID.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

import java.util.concurrent.atomic.AtomicInteger

data class Product(val name: String, val price: Double, var id: Int)

object Repo: ConcurrentHashMap<Int, Product>() {
    private val idCounter = AtomicInteger()
    fun addProduct(product: Product) {
        product.id = idCounter.incrementAndGet()
        put(product.id, product)
    }
}

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("build/classes"), module= Application::mymodule).start(true)
}

fun Application.mymodule() {
    install(DefaultHeaders)
    install(CORS) {
        maxAgeInSeconds = Duration.ofDays(1).toSeconds()
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
    intercept(ApplicationCallPipeline.Call) {
        if (call.request.uri == "/")
            call.respondHtml {
                body {
                    h2 {
                        a(href = "/products") { + "Go to /products" }
                    }
                }
            }
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
            } catch(ex :NumberFormatException) {
                call.respond(HttpStatusCode.BadRequest,
                    """{"error":"Invalid product id: ${call.parameters["id"]}"}""")
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

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
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

    install(ContentNegotiation) {
        gson {
        }
    }

    val client = HttpClient() {
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }

       // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

import com.fasterxml.jackson.databind.SerializationFeature
import kotlinx.serialization.Serializable
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.BrowserUserAgent
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.timeout
import io.ktor.http.content.default
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.webjars.Webjars
import io.ktor.websocket.webSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.html.currentTimeMillis
import kotlinx.serialization.json.Json
import java.time.Duration
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val client = HttpClient(CIO) {
        install(HttpTimeout) {
        }
        install(Auth) {
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
        BrowserUserAgent() // install default browser-like user-agent
        // install(UserAgent) { agent = "some user agent" }
    }
    runBlocking {
        // Sample for making a HTTP Client request
        /*
        val message = client.post<JsonSampleClass> {
            url("http://127.0.0.1:8080/path/to/endpoint")
            contentType(ContentType.Application.Json)
            body = JsonSampleClass(hello = "world")
        }
        */
    }

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Webjars) {
//        path = "assets" //defaults to /webjars
//        zone = ZoneId.of("EST") //defaults to ZoneId.systemDefault()
    }
    install(Routing) {
        get("/blog") {
            call.respondText("My Example Blog2", ContentType.Text.Html)
        }
    }

    routing {
        static("/") {
            files("static")
            default("static/index.html")
        }
        get("/hello") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        webSocket("/myws/quotes") {
            val quotes = quotesPublisher();
            // Json also has .Default configuration which provides more reasonable settings,
            // but is subject to change in future versions
            val json = Json
            quotes.consumeEach {
                // serializing objects
                val jsonData = json.encodeToString(Quote.serializer(), it)
                send(Frame.Text(jsonData))
            }

            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }
    }
}


suspend inline fun CoroutineScope.numPublisher(): ReceiveChannel<Int> = produce {
    for (i in 1..10) {
        send(i)
        delay(1000)
    }
}

@Serializable
data class Quote(val symbol: String, val price: Double, val instant: Long = System.nanoTime()) {
    var id: Int? = null
}

var quotes = listOf(
    Quote("VMW", 215.35),
    Quote("GOOG", 309.17),
    Quote("CTXS", 112.11),
    Quote("DELL", 92.93),
    Quote("MSFT", 255.19),
    Quote("ORCL", 115.72),
    Quote("RHT", 111.27)
)

suspend inline fun CoroutineScope.quotesPublisher(): ReceiveChannel<Quote> = produce {
    val rand = Random()
    for (i in 1..800) {
        var quote: Quote = quotes.get(i % quotes.size)
        val currentTime = System.currentTimeMillis()
        quote =
            quote.copy(price = quote.price * (0.9 + 0.2 * rand.nextDouble()), instant = currentTime)
        send(quote)
        delay(300)
    }
}

//suspend inline fun CoroutineScope.quotesPublisher(): ReceiveChannel<Quote> = Channel<Quote>().apply {
//    val rand = Random()
//    launch {
//        for (i in 1..800) {
//            var quote: Quote = quotes.get(i % quotes.size)
//            quote =
//                quote.copy(price = quote.price * (0.9 + 0.2 * rand.nextDouble()), instant = System.currentTimeMillis())
//            send(quote)
//            delay(300)
//        }
//    }
//}

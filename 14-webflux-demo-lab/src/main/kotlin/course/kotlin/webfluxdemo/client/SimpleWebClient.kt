package course.kotlin.webfluxdemo.client

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions
import org.springframework.web.reactive.function.client.WebClient


fun main() {
    val baseUrl = "http://localhost:8080"
    val client = WebClient.builder().baseUrl(baseUrl).build()
    println("SimpleWebClient constructed @ $baseUrl")
    client.get()
        .uri("/simple/numbers")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .retrieve()
        .bodyToFlux(Number::class.java)
        .doOnNext { event: Any -> println("Events received: $event") }
        .doOnError { err: Throwable -> println("Error stopping user service: $err") }
        .doOnComplete { println("Client complete.") }
        .blockLast();
}

private fun adminCredentials(): ExchangeFilterFunction {
    return ExchangeFilterFunctions.basicAuthentication("admin", "admin")
}


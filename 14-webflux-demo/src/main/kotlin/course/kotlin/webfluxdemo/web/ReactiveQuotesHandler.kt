package course.kotlin.webfluxdemo.web

import course.kotlin.webfluxdemo.model.Quote
import course.kotlin.webfluxdemo.services.QuotesGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ReactiveQuotesHandler ( val generator: QuotesGenerator){

    fun streamQuotes(request: ServerRequest?): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(generator.getQuotesStream(Duration.ofMillis(250)), Quote::class.java)
    }

    fun streamQuotesSSE(request: ServerRequest?): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(generator.getQuotesStream(Duration.ofMillis(250)), Quote::class.java)
    }
}


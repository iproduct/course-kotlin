package course.kotlin.webfluxdemo.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.router

@Configuration
class QuotesRouterConfig(private val handler: QuotesHandler) {

    @Bean
    fun quotesRouter() = router {
        GET(accept(APPLICATION_STREAM_JSON) and "/api/quotes", handler::streamQuotes)
        GET(accept(TEXT_EVENT_STREAM) and "/api/quotes", handler::streamQuotesSSE)
    }
}

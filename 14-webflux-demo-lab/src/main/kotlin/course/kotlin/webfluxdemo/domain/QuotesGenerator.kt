package course.kotlin.webfluxdemo.domain

import course.kotlin.webfluxdemo.model.Quote
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@Service
class QuotesGenerator {
    private val rand = Random()

    var quotes: List<Quote> = Arrays.asList<Quote>(
        Quote("VMW", 215.35),
        Quote("GOOG", 309.17),
        Quote("CTXS", 112.11),
        Quote("DELL", 92.93),
        Quote("MSFT", 255.19),
        Quote("ORCL", 115.72),
        Quote("RHT", 111.27)
    )

    fun getQuotesStream(period: Duration): Flux<Quote> {
        return Flux.interval(period)
            .map { index: Long ->
                val quote: Quote = quotes[index.toInt() % quotes.size]
                quote.copy(price = quote.price * (0.9 + 0.2 * rand.nextDouble()))
            }
            .share()
            .log()
    }
}

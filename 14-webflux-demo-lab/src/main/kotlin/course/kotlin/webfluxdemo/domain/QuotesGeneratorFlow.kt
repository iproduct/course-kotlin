package course.kotlin.webfluxdemo.domain

import course.kotlin.webfluxdemo.model.Quote
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@Service
class QuotesGeneratorFlow {
    private val rand = Random()

    var quotes: MutableList<Quote> = Arrays.asList<Quote>(
        Quote("VMW", 215.35),
        Quote("GOOG", 309.17),
        Quote("CTXS", 112.11),
        Quote("DELL", 92.93),
        Quote("MSFT", 255.19),
        Quote("ORCL", 115.72),
        Quote("RHT", 111.27)
    )

    fun getQuotesStream(period: Duration): Flow<Quote> =
        flow {
            println("Flow started")
            for (i in 1..10000) {
                delay(250)
                emit(i)
            }
        }.map {
            val index = (it.toInt() - 1) % quotes.size
            var quote: Quote = quotes[index]
            quote = quote.copy(price = quote.price * (0.9 + 0.2 * rand.nextDouble()))
            quotes[index] = quote
            quote
        } //.shareIn(this, SharingStarted.Lazily)
}

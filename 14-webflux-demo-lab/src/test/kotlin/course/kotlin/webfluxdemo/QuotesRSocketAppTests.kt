package course.kotlin.webfluxdemo

import app.cash.turbine.test
import course.kotlin.webfluxdemo.model.Quote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlow
import java.net.URI

class QuotesRSocketAppTests {
//    @LocalServerPort
//    var serverPort: Int = 0

    @Autowired
    lateinit var rsocketBuilder: RSocketRequester.Builder

    @ExperimentalCoroutinesApi
    @Test
    fun `test that messages API streams latest messages`() {
        runBlocking {
            val rSocketRequester =
                rsocketBuilder.websocket(URI("ws://localhost:7000/rsocket"))

            rSocketRequester
                .route("quotes.stream")
                .retrieveFlow<Quote>()
                .test {
                    assertEquals("VMW", awaitItem().symbol)
                    cancelAndIgnoreRemainingEvents()
//                    awaitComplete()
//                    expectNoEvents()
                }
        }
    }
}



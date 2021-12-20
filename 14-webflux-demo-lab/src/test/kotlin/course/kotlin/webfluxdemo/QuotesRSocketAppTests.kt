package course.kotlin.webfluxdemo

import app.cash.turbine.test
import course.kotlin.webfluxdemo.model.Quote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.rsocket.context.LocalRSocketServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.connectWebSocketAndAwait
import org.springframework.messaging.rsocket.retrieveFlow
import java.net.URI

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuotesRSocketAppTests(
    @Autowired val rsocketBuilder: RSocketRequester.Builder,
    @LocalServerPort val serverPort: Int
) {

    lateinit var rsocketClient: RSocketRequester

    @BeforeEach
    fun initRSocketClient() {
        rsocketClient = rsocketBuilder.websocket(URI("ws://localhost:${serverPort}/rsocket"))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test that messages API streams latest messages`() {
        runBlocking {
            rsocketClient
                .route("quotes.stream")
                .retrieveFlow<Quote>()
                .test {
                    assertEquals("VMW", awaitItem().symbol)
                    assertEquals("GOOG", awaitItem().symbol)
                    assertEquals("CTXS", awaitItem().symbol)
                    assertEquals("DELL", awaitItem().symbol)
                    assertEquals("MSFT", awaitItem().symbol)
                    assertEquals("ORCL", awaitItem().symbol)
                    assertEquals("RHT", awaitItem().symbol)
                    assertEquals("VMW", awaitItem().symbol)
                    cancelAndIgnoreRemainingEvents()
//                    awaitComplete()
//                    expectNoEvents()
                }
        }
    }
}



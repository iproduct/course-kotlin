package course.kotlin.webfluxdemo.web

import course.kotlin.webfluxdemo.domain.QuotesGeneratorFlow
import course.kotlin.webfluxdemo.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onStart
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.time.Duration

@Controller
@MessageMapping("quotes")
class QuotesRSocket (val generator: QuotesGeneratorFlow) {

//    @MessageMapping("stream")
//    suspend fun receive(@Payload inboundMessages: Flow<MessageVM>) =
//        messageService.post(inboundMessages)

    @MessageMapping("stream")
    fun send(): Flow<Quote> = generator
        .getQuotesStream(Duration.ofMillis(250))
//        .onStart {
//            emitAll(messageService.latest())
//        }
}

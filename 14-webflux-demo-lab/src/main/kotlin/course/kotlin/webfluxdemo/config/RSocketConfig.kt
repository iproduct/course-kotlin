package course.kotlin.webfluxdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.codec.Decoder
import org.springframework.core.codec.Encoder
import org.springframework.http.codec.cbor.Jackson2CborDecoder
import org.springframework.http.codec.cbor.Jackson2CborEncoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.web.util.pattern.PathPatternRouteMatcher

@Configuration
class RSocketConfig {
    @Bean
    fun rsocketStrategies(): RSocketStrategies {
        return RSocketStrategies.builder()
            .encoders { encoders: MutableList<Encoder<*>?> ->
                encoders.add(
                    Jackson2JsonEncoder()
                )
            }
            .decoders { decoders: MutableList<Decoder<*>?> ->
                decoders.add(
                    Jackson2JsonDecoder()
                )
            }
            .routeMatcher(PathPatternRouteMatcher())
            .build()
    }
}

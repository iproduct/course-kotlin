package course.kotlin.webfluxdemo.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class SimpleRouterConfig {
    @Bean
    fun route() = router {
        GET("/route") { _ ->
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                fromValue(arrayOf(1, 2, 3))
//                fromPublisher(
//                    Flux.zip(Flux.range(1, 10), Flux.interval(Duration.ofMillis(500))).map{it.t1},
//                    Int::class.java
//                )
            )
        }
    }
}

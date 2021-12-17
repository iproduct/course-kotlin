package course.kotlin.webfluxdemo.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
@RequestMapping("/simple")
class SimpleReactiveUsingAnnotations {

    @GetMapping(path = ["/numbers"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getNumbers(): Flux<Int> {
        return Flux.range(1, 10).zipWith(Flux.interval(Duration.ofMillis(500)))
            .map{it.t1}
//       return Flux.interval(Duration.ofMillis(1000)).map{it.toInt()}
    }
}

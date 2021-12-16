package course.kotlin.webfluxdemo

import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.SynchronousSink
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class ReactiveExamples {
}
object ReactorDemo {
    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val ints = Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1))
            .map { i: Long ->
                if (i < 5) return@map i
                throw RuntimeException("Got to be 4")
            }
        //        ints.subscribe(
//            i -> System.out.println(i),
//            err -> System.err.println(err.getMessage()),
//            () -> System.out.println("Stream completed.")
//        );
        val flux = Flux.generate(
            { 0 }
        ) { state: Int, sink: SynchronousSink<String?> ->
            sink.next("3 x " + state + " = " + 3 * state)
            if (state == 10) sink.complete()
            state + 1
        }

//        flux.delayElements(Duration.ofSeconds(1))
//            .subscribe(
//                System.out::println,
//                err -> System.err.println(err.getMessage()),
//                () -> System.out.println("Stream completed.")
//            );
        val sink = Sinks.many().multicast().onBackpressureBuffer<String>()
        val result = sink.asFlux()
            .map { obj: String ->
                obj.uppercase(
                    Locale.getDefault()
                )
            }
            .delayElements(Duration.of(1000, ChronoUnit.MILLIS))
            .filter { s: String -> s.startsWith("HELLO") }
        result.map { data: String -> "Subscriber 1: $data" }.subscribe { x: String? ->
            println(
                x
            )
        }
        result.map { data: String -> "Subscriber 2: $data" }.subscribe { x: String? ->
            println(
                x
            )
        }
        sink.tryEmitNext("Hello World!") // emit - non blocking
        sink.tryEmitNext("Goodbye World!")
        sink.tryEmitNext("Hello Trayan!")
        Thread.sleep(18000)
    }
}

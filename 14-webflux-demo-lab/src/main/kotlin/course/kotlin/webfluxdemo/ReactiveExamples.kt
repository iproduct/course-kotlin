package course.kotlin.webfluxdemo

import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.SynchronousSink
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

fun main(args: Array<String>) {
    // Generate Flux using interval factory function
    val ints = Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1))
        .map {
            if (it == 5L) throw RuntimeException("Error: Got to be 4")
            it
        }
//    ints.subscribe(
//        { System.out.println(it) },
//        { System.err.println(it.message) },
//        { System.out.println("Stream completed.") }
//    );

    // Generate Flux using generate function
    val flux = Flux.generate({ 0 }) { state: Int, sink: SynchronousSink<String?> ->
        sink.next("3 x " + state + " = " + 3 * state)
        if (state == 10) sink.complete()
        state + 1
    }
//    flux.delayElements(Duration.ofMillis(300)).subscribe(
//        { System.out.println(it) },
//        { System.err.println(it.message) },
//        { System.out.println("Stream completed.") }
//    );

    val sink = Sinks.many().multicast().onBackpressureBuffer<String>()
    val result = sink.asFlux()
//        .publishOn(Schedulers.single())
        .subscribeOn(Schedulers.single())
        .map { it.uppercase() }
        .delayElements(Duration.of(1000, ChronoUnit.MILLIS))
        .filter { s: String -> s.startsWith("HELLO") }

    result.map { data: String -> "Subscriber 1: $data" }.subscribe(System.out::println)
    result.map { data: String -> "Subscriber 2: $data" }.subscribe(System.out::println)

    sink.tryEmitNext("Hello World!") // emit - non blocking
    sink.tryEmitNext("Hello Kotlin!") // emit - non blocking
    sink.tryEmitNext("Hello Reactor!") // emit - non blocking
    sink.tryEmitNext("Goodbye World!")
    sink.tryEmitNext("Hello Trayan!")

    Thread.sleep(18000)
}


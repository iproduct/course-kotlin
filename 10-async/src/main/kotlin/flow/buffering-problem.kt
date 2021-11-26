package flow.buffering

import jdk.nashorn.internal.objects.ArrayBufferView.buffer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

private fun foo(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(300)
        println("Emitting: $i from ${coroutineContext[Job]}")
        emit(i)
    }
}


fun main() = runBlocking<Unit>(CoroutineName("collector")) {
    var time = measureTimeMillis {
        println("Calling foo collect")
        foo()
            .buffer(0)
            .collect {
            delay(1000)
            println("Collected: $it from ${kotlin.coroutines.coroutineContext[Job]}")
        }
    }
    println("Collected in $time ms")
}

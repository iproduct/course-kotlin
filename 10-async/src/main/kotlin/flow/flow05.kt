package flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

private fun foo(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        print("Emitting: $i from ${coroutineContext[Job]}")
        emit(i)
    }
}

fun main() = runBlocking<Unit>(CoroutineName("main")){
    println("Calling foo...")
    val flow = foo()
    println("Calling collect...")
    flow.collect { println("Collected: $it") }
    println("Calling collect again...")
    flow.collect { println("Collected: $it") }
    println("Calling collect again...")
    flow.collect { println("Collected: $it") }
}

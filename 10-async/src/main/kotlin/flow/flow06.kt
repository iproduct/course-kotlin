package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

private fun foo(): Flow<Int> = flow {
    try {
        for (i in 1..10) {
            delay(100)
            println("Emitting $i")
            emit(i)
        }
    } catch (e: Exception) {
        println(e)
    } finally {
        println("Exiting flow producer.")
    }

}

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(350) { // Timeout after 250ms
        foo().collect { value -> println(value) }
    }
    println("Done")
}

package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

private suspend fun foo(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3, 4, 5)
}

fun main() = runBlocking<Unit> {
    foo().forEach { value -> println(value) }
}

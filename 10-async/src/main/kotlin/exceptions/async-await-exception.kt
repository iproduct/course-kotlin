package exceptions

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    try {
        println(failedConcurrentSum())
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async {
        try {
            delay(5000) // Emulates very long computation
            42
        } catch(ex: CancellationException) {
            println("First child was cancelled")
            throw ex
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
//        108
        throw ArithmeticException()
    }
    one.await() + two.await()
}

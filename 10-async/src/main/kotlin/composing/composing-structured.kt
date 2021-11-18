package composing.async

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

fun main() {
        runBlocking {
            val time = measureTimeMillis {
                println(coroutineContext[Job])
                println(coroutineContext)
                val time = measureTimeMillis {
                    println("The answer is ${concurrentSum()}")
                }
                println("Completed in $time ms")
            }
        }

}

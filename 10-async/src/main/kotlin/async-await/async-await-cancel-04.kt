package async.cancel

import async.compute
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        val deferred = async { compute() }
        launch{
            delay(1500)
            deferred.cancel()
        }
        try {
            println("${deferred.await()}")
        }catch (ex: CancellationException){
            println("Computing the answer of everything is cancelled!")
        }
    }
    println("Completed in $time ms")
}

suspend fun compute() = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    "The answer is ${one.await() + two.await()}"
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 24
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 18
}


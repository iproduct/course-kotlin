package exceptions

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
    }
    val job = GlobalScope.launch(handler + CoroutineName("Global Job 1")) { // root coroutine with launch
        println("Throwing exception from launch -> ${coroutineContext.job}")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
//    try { // doesn't work with jobs
        job.join()
//    } catch (e: IndexOutOfBoundsException) {
//        println("Caught IndexOutOfBoundsException")
//    }
    println("Joined failed job")

    val deferred = GlobalScope.async(CoroutineName("Global Deferred 2")) { // root coroutine with async
        println("Throwing exception from async -> ${coroutineContext.job}")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    try {
        val result = deferred.await()
        println("Unreached - $result")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}

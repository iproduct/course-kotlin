package course.kotlin.errors


import course.kotlin.cancelation.helloEarth2
import course.kotlin.helloEarth
import javafx.application.Application.launch
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        val galaxyHelloJob = launch {
            helloGalaxy()
        }
        val earthJob = launch(CoroutineName("Earth")) {
            delay(4000)
            helloEarth2()
        }
        println("Hello -> Thread: ${Thread.currentThread().name}")
        earthJob.join()
        println("Earth joined successfully -> Thread: ${Thread.currentThread().name}")
        galaxyHelloJob.cancelAndJoin()
        println("End of Demo.")
    }
    println("Completed in $time ms.")
}

suspend fun helloGalaxy() {
    var handler = CoroutineExceptionHandler { ctx, ex ->
        println("Coroutine ${ctx.job} got exception $ex.")
    }
    supervisorScope {
        for (i in 1..10) {
            launch(CoroutineName("World $i") + Dispatchers.Default + handler) {
                helloWorld(i)
            }
        }
    }
}

suspend fun helloWorld(i: Int) {
    try {
        delay(i * 1000L)
//    if( i == 2) throw IllegalStateException("Canceled from World $i !!!")
    } finally { // close resources and finish
        withContext(NonCancellable) {
            println("Traying to close resources for World $i!-> Thread: ${Thread.currentThread().name} ...")
            delay(2000)
            println("World $i! -> Job ${kotlin.coroutines.coroutineContext.job}, Thread: ${Thread.currentThread().name}")
        }
    }
}

suspend fun helloEarth2() {
    delay(1000)
    println("Earth! ->  Job ${coroutineContext.job}, Thread: ${Thread.currentThread().name}")
    println("Earth complete!")
}

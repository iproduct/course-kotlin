package course.kotlin


import javafx.application.Application.launch
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            val galaxyHelloJob = launch(Dispatchers.Unconfined) {
                helloGalaxy()
//                helloEarth()
            }
            val earthJob = GlobalScope.launch {
                delay(7000)
                helloEarth()
            }
            println("Hello -> Thread: ${Thread.currentThread().name}")
//            delay(15000)
            earthJob.join()
            // galaxyHelloJob.ensureActive()
            galaxyHelloJob.cancel()
            println("End of Demo.")
        }
    }
    sleep(20000)
    println("Completed in $time ms.")
}

suspend fun helloGalaxy() {
    coroutineScope {
        for (i in 1..10) {
            launch(Dispatchers.Default) {
                helloWorld(i)
            }
        }
    }
}

suspend fun helloWorld(i: Int) {
    delay(i * 1000L)
    println("World $i! -> Thread: ${Thread.currentThread().name}")
}

suspend fun helloEarth() {
    delay(1000)
    println("Earth! -> Thread: ${Thread.currentThread().name}")
    while(true) {
        delay(100)
    }
    println("Earth complete!")
}

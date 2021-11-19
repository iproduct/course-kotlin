package course.kotlin.cancelation


import course.kotlin.helloEarth
import javafx.application.Application.launch
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        val galaxyHelloJob = launch(start = CoroutineStart.DEFAULT) {
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
    coroutineScope {
        for (i in 1..10) {
            launch(CoroutineName("World $i")) {
                helloWorld(i)
            }
        }
    }
}

suspend fun helloWorld(i: Int) {
    var nextPrintTime = System.currentTimeMillis()
    var j = 0
    while (j < 10 ){
//        yield()
        if (System.currentTimeMillis() > nextPrintTime){
            println("Job $i: ${coroutineContext.job}, Thread: ${Thread.currentThread().name}: I'm working ${j++}")
            nextPrintTime += 1000
        }
    }
    println("World $i! -> Job ${coroutineContext.job}, Thread: ${Thread.currentThread().name}")
}

suspend fun helloEarth2() {
    delay(1000)
    println("Earth! ->  Job ${coroutineContext.job}, Thread: ${Thread.currentThread().name}")
    println("Earth complete!")
}

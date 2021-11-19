package course.kotlin


import javafx.application.Application.launch
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureTimeMillis

fun main() = runBlocking() {
    val time = measureTimeMillis {
        val helloGalaxyJob = launch(Dispatchers.IO, start = CoroutineStart.LAZY) { doGalaxy() }
        val earthJob = launch { doEarth() }
        println("Hello -> Thread: ${Thread.currentThread().name}")
        earthJob.join()
//        helloGalaxyJob.start()
//        helloGalaxyJob.cancel()
        helloGalaxyJob.join()
        println("End")
    }
    println("Competed in $time ms")
}

suspend fun doGalaxy() {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    supervisorScope {
        for (i in 1..10) {
            launch(handler) { doWorld(i) }
        }
    }
}

suspend fun doWorld(i: Int) {
    delay(i * 1000L)
    if (i == 3) throw IllegalStateException("Canceled from Child $i")
    println("World $i!-> Thread: ${Thread.currentThread().name}")
}

suspend fun doEarth() {
    delay(4500)
    println("Earth!-> Thread: ${Thread.currentThread().name}")
}


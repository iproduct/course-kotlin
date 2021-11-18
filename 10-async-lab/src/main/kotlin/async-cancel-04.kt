package course.kotlin.cancel

import kotlinx.coroutines.*
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
//    val handler = CoroutineExceptionHandler { _, exception ->
//        println("CoroutineExceptionHandler got $exception")
//    }
    coroutineScope {
        for (i in 1..10) {
            launch { doWorld(i) }
        }
    }
}

suspend fun doWorld(i: Int) {
    var nextPrintTime = System.currentTimeMillis()
//    if (i == 3) throw IllegalStateException("Canceled from Child $i")
    println("World $i!-> Thread: ${Thread.currentThread().name}")
}

suspend fun doEarth() {
    delay(4500)
    println("Earth!-> Thread: ${Thread.currentThread().name}")
}


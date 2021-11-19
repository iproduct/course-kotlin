package course.kotlin.cancel

import course.kotlin.doEarth
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

fun main() = runBlocking() {
    val time = measureTimeMillis {
        val helloGalaxyJob = launch { doGalaxy() }
        val earthJob = launch { doEarth() }
        println("Hello -> Job: : ${coroutineContext[Job]} Thread: ${Thread.currentThread().name}")
        earthJob.join()
        helloGalaxyJob.cancelAndJoin()
        println("End")
    }
    println("Competed in $time ms")
}

suspend fun doGalaxy() {
    coroutineScope {
        for (i in 1..10) {
            launch(Dispatchers.Default) { doWorld(i) }
        }
    }
}

suspend fun doWorld(i: Int) {
    var nextPrintTime = System.currentTimeMillis()
    var j = 0
//    while (j < 10 && coroutineContext.isActive) {
    try {
        while (j < 10) {
            yield()
//        coroutineContext.ensureActive()
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Job $i : ${coroutineContext[Job]}, Thread: ${Thread.currentThread().name}: I'm working ${j++}")
                nextPrintTime += 1000L
            }
        }
    } finally { // close resources and finish
        withContext(NonCancellable) {
            println("Traying to close resources for World $i!-> Thread: ${Thread.currentThread().name} ...")
            delay(2000)
            println("Closed World $i!-> Thread: ${Thread.currentThread().name}")
        }
    }
}

suspend fun doEarth() {
    delay(4500)
    println("Earth!-> Job: : ${coroutineContext[Job]} Thread: ${Thread.currentThread().name}")
}


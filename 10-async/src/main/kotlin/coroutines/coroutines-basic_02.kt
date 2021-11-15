package course.kotilin.async.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

// Sequentially executes doWorld followed by "Done"
fun main() {
    val time = measureTimeMillis {
        runBlocking {
            doWorld()
            println("Done")
        }
    }
    println("Completed in $time ms")
}

// Concurrently executes both sections
suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}

package course.kotlin

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
    runBlocking(Dispatchers.Main) {
        val job1 = launch(Dispatchers.IO) {
            delay(1000)
            println("World 1!")
//            while (true) {
//                delay(1000)
//            }
        }
        val job2 = launch(Dispatchers.IO) {
            delay(2000)
            println("World 2!")
//            while (true) {
//                delay(1000)
//            }
        }
        joinAll(job1, job2)
        println("Hello")
        delay(900)
        println("End")
    }
}

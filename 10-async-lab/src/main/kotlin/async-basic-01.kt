package course.kotlin

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
    runBlocking(Dispatchers.Main) {
        launch(Dispatchers.IO) {
            delay(1000)
            println("World!")
//            while (true) {
//                delay(1000)
//            }
        }
        println("Hello")
        delay(900)
        println("End")
    }
}

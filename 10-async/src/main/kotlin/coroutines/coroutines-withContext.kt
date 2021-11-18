package course.kotilin.async.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        println("in sub coroutine ${Thread.currentThread().name}")
    }
    println("before coroutine in main ${Thread.currentThread().name}")
    withContext(Dispatchers.IO) {
        println("hello from coroutine ${Thread.currentThread().name}")
        delay(1500)
        println("hello from coutoutine after delay ${Thread.currentThread().name}")
    }
    println("after coroutine in main ${Thread.currentThread().name}")
}

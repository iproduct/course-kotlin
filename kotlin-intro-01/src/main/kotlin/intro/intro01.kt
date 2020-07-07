package intro

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> { // start flow.intro.main coroutine
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // flow.intro.main coroutine continues here immediately
    delay(5000L)      // delaying for 2 seconds to keep JVM alive
    println("End")
}

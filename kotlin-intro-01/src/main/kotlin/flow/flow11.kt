package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOG: Logger = LoggerFactory.getLogger("org.example.kotlin-intro-01.main")

fun main() = runBlocking<Unit>(Dispatchers.Default) {
    launch(CoroutineName("My-Coroutine")) {
        flowOf("A", "B", "C")
            .map {
                delay(1000)
                it
            }
            .onEach {
                log(
                    "[${coroutineContext[CoroutineName.Key]} is executing on thread : " +
                            "${Thread.currentThread().name}]: Collected  1$it"
                )
            }
            .buffer()  // <--------------- buffer between onEach and collect
            .collect {
                log(
                    "[${coroutineContext[CoroutineName.Key]} is executing on thread : " +
                            "${Thread.currentThread().name}]: Collected  2$it"
                )
            }
    }
}

private fun log(s: String) {
    LOG.info(s)
}

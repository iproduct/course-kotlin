package flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOG: Logger = LoggerFactory.getLogger("org.example.kotlin-intro-01.main")

fun main() = runBlocking(Dispatchers.Default) {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        repeat(5) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 500L) // variable delay 200ms, 400ms, 600ms
                log("Coroutine $i is done")
            }
        }
        log("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    log("Now processing of the request is complete")
}

private fun log(s: String) {
    LOG.info(s)
}

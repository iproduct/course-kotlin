package flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import logging.Log
import logging.log
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOG: Logger = LoggerFactory.getLogger("org.example.kotlin-intro-01.main")

private fun foo(): Flow<Int> = flow {
    log("[${this}]: Started foo flow")
    for (i in 1..3) {
        emit(i)
    }
}


fun main() = runBlocking<Unit>(Dispatchers.Default + Log("flow10")) {
    foo().collect { value -> log("[$this]: Collected $value") }
}

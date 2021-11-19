package context

import kotlinx.coroutines.*
import logging.Log
import logging.log
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

//private val LOG: Logger = LoggerFactory.getLogger("context.main")
fun main() = runBlocking(Log("context.main")) {
    log("Started main coroutine")
// run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}

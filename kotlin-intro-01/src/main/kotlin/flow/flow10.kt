package flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

//private val LOG: Logger = LoggerFactory.getLogger("org.example.kotlin-intro-01.main")
private val log = KotlinLogging.logger {}

private fun foo(): Flow<Int> = flow {
    log.info("[${this}]: Started foo flow")
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking<Unit>(Dispatchers.Default) {
    foo().collect { value -> log.info("[$this]: Collected $value") }
}


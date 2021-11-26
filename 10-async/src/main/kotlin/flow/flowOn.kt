package flow.on

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.coroutineContext

private fun foo(): Flow<Int> = flow {
        println("Flow started")
        for (i in 1..3) {
            delay(100)
            println("Emitting: $i from ${coroutineContext[Job]}")
            emit(i)
        }
    }.flowOn(Dispatchers.Default)


fun main() = runBlocking<Unit> {
    foo().collect {
        println("Collecting: $it on ${coroutineContext[Job]}")
    }
}

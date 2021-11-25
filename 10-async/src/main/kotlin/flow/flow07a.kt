package flow.finally

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

private fun numbers(): Flow<Int> = flow {
    try {
        println("Emitting 1: Coroutine: ${currentCoroutineContext().job}, Thread: ${Thread.currentThread().name}")
        emit(1)
        println("Emitting 2: Coroutine: ${currentCoroutineContext().job}, Thread: ${Thread.currentThread().name}")
        emit(2)
        println("This line will not execute: Coroutine: ${currentCoroutineContext().job}, Thread: ${Thread.currentThread().name}")
        emit(3)
    } catch (e: Exception) {
        println(e)
    } finally {
        println("Finally in numbers")
    }
}.flowOn(Dispatchers.Default)

fun main() = runBlocking<Unit> {
    numbers()
        .take(2) // take first 2
        .collect {
            println("Collecting $it, Coroutine: ${kotlin.coroutines.coroutineContext.job}, Thread: ${Thread.currentThread().name}")
        }
    println("Done")
}



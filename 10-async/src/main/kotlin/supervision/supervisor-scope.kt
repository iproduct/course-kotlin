package course.kotilin.async.coroutines.supervisor

import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        supervisorScope {
            val firstChild = launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            println("Throwing an exception from the scope")

            val secondChild = launch {
                try {
                    firstChild.join()
                    // Cancellation of the first child is not propagated to the second child
                    println("The second child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    // But cancellation of the supervisor is propagated
                    println("The second child is cancelled because the supervisor was cancelled")
                }
            }
            yield()

            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught an assertion error")
    }
}

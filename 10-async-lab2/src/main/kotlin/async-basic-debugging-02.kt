package course.kotlin.debugging

import kotlinx.coroutines.*


fun main() = runBlocking(CoroutineName("main_coroutine")) {
    var job1 = launch(CoroutineName("job1") ) {
        delay(1000)
        println("World 1 -> Job: ${coroutineContext[Job]}, running on Thread: ${Thread.currentThread().name}")
    }
    var job2 = launch((CoroutineName("job2") )) {
        delay(2000)
        println("World 2 -> Job: ${coroutineContext[Job]}, running on Thread: ${Thread.currentThread().name}")
    }
//    job1.join()
//    job2.join()
    println("Hello -> Job: ${coroutineContext[Job]}, running on Thread: ${Thread.currentThread().name}")
    joinAll(job1, job2)
    println("End of demo -> Job: ${coroutineContext[Job]}, running on Thread: ${Thread.currentThread().name}")
}

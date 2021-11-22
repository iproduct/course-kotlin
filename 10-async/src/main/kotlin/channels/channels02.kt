package channels

import jdk.nashorn.internal.objects.ArrayBufferView.buffer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*


fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..10) channel.send(x * x)
        channel.close()
    }

    val ctx = coroutineContext

//    for (y in channel) println(y)

// here we print five received integers:
//    val cit = channel.iterator()
//    while(cit.hasNext()){
//        println(cit.next())
//    }

//    while(true){
//        val cres = channel.receiveCatching()
//        if (cres.isClosed) break
//        println(cres.getOrNull())
//    }
//    channel.consumeEach { println(it) }

//    channel.consumeAsFlow().collect { println(it) }

//    channel.consume {
//        repeat(5) {println(this.receive())}
//    }

    //publish-subscribe
    val sharedFlow = channel.consumeAsFlow()
        .buffer(0)
        .onCompletion { ctx.cancelChildren() } // cancel children coroutines}
        .shareIn(this, SharingStarted.Eagerly)
    val j1 = launch{ sharedFlow.collect { println(it) }}
    delay(10)
    val j2 = launch{ sharedFlow.collect { println(it) }}
    val j3 = launch { sharedFlow.collect { println(it) }}
    joinAll(j1, j2, j3)

    // fan-out
//    val sharedFlow = channel.receiveAsFlow()
//        .buffer(0)
////        .onCompletion { ctx.cancelChildren() } // cancel children coroutines}
//    val j1 = launch{ sharedFlow.collect { println("$it -> ${coroutineContext.job}") }}
//    val j2 = launch{ sharedFlow.collect { println("$it -> ${coroutineContext.job}") }}
//    val j3 = launch { sharedFlow.collect { println("$it -> ${coroutineContext.job}") }}
//    joinAll(j1, j2, j3)


    println("Done!")
}

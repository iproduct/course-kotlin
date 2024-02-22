package channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch { // launch sender coroutine
        try {
            repeat(10) {
                print("Sending $it ") // print before sending each element
                channel.send(it) // will suspend when buffer is full
            }
        } catch (ex: CancellationException) {
            print("Sender was cancelled! ")
            channel.close()
        }
    }
// don't receive anything... just wait....
    delay(1000)
    sender.cancelAndJoin() // cancel sender coroutin
    for (y in channel) print(y.toString() + " ")
    println("Done! ")
}


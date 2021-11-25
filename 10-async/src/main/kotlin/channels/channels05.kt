package channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    fun CoroutineScope.produceNumbers(limit: Int) = produce<Int> {
        var x = 1
        while (x <= limit) send(x++) // infinite stream of integers starting from 1
    }

    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) send(x * x)
    }

    fun CoroutineScope.runningSum(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        var sum = 0
        for (x in numbers) {
            sum += x
            send(sum)
        }
    }

    // here we print received values using `for` loop (until the channel is closed)
    for (y in square(runningSum(produceNumbers(10)))) println(y)
    println("Done!")
}

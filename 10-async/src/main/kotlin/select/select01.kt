package select

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.fizz() = produce<String> {
    while (true) { // sends "Fizz" every 300 ms
//        ensureActive()
        delay(300)
        send("Fizz")
    }
}

fun CoroutineScope.buzz() = produce<String> {
    while (true) { // sends "Buzz!" every 500 ms
        delay(500)
        send("Buzz!")
    }
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) =
    select<String> { // <Unit> means that this select expression does not produce any result
        fizz.onReceive { value ->  // this is the first select clause
            println("fizz -> '$value'")
            value
        }
        buzz.onReceive { value ->  // this is the second select clause
            println("buzz -> '$value'")
            value
        }
    }

fun main() = runBlocking {
    val fizz = fizz()
    val buzz = buzz()
    repeat(10) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren() // cancel fizz & buzz coroutines
}

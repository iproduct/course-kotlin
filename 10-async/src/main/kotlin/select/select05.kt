package select.onawawait

import javafx.application.Application.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.selects.selectUnbiased
import kotlin.random.Random

fun CoroutineScope.asyncString(time: Int, number: Int) = async {
    delay(time.toLong())
    "$number waited for $time ms"
}

fun CoroutineScope.asyncStringsList(): MutableList<Deferred<String>> {
    val random = Random(System.currentTimeMillis())
    return MutableList(12) { asyncString(random.nextInt(1000), it) }
}

fun main() = runBlocking {
    val list = asyncStringsList()
    val countActive = list.count { it.isActive }
    while(countActive > 0) {
        val result = select<Pair<Int, String>> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    index to "Deferred produced answer '$answer'"
                }
            }
        }
        println(result.second)
        list.removeAt(result.first)
        val countActive = list.count { it.isActive }
        println("$countActive coroutines are still active")
    }
}

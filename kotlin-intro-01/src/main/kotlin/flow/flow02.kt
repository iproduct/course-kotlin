package flow

private fun foo(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..5) {
        Thread.sleep(200) // pretend we are computing it
        yield(i) // yield next value
    }
}

fun main() {
    foo().forEach { value -> println(value) }
}

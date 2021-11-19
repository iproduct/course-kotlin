package flow

private fun foo(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..5) {
        Thread.sleep(200) // pretend we are computing it
        yield(i) // yield next value
    }
}.constrainOnce()

fun main() {
    val seq = foo()
    seq.forEach { value -> println(value) }
    seq.forEach { value -> println(value) }
}

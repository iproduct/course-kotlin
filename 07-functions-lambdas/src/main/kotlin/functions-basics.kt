package course.kotlin.functions

import java.util.stream.Stream

fun double(x: Int): Int {
    return 2 * x
}

fun powerOf(number: Int, exponent: Int): Int { /*...*/ return 42}
fun powerOf2(
    number: Int,
    exponent: Int, // trailing comma
) { /*...*/ }


fun main1() {
    val result = double(2)
    Stream().read() // create instance of class Stream and call read()

}


//default args
fun read(
    b: ByteArray,
    off: Int = 0,
    len: Int = b.size,
) { /*...*/ }

open class A {
    open fun foo(i: Int = 10) { /*...*/ }
}

class B : A() {
    override fun foo(i: Int) { /*...*/ }  // No default value is allowed.
}

fun foo(
    bar: Int = 0,
    baz: Int,
) { /*...*/ }

fun main2() {
    foo(baz = 1) // The default value bar = 0 is used
}

fun foo(
    bar: Int = 0,
    baz: Int = 1,
    qux: () -> Unit,
) { /*...*/ }
fun main() {
    foo(1) { println("hello") }     // Uses the default value baz = 1
    foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
    foo { println("hello") }        // Uses both default values bar = 0 and baz = 1

}

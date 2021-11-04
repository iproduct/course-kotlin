package course.kotlin.functions

fun printHello(name: String?): Unit {
    if (name != null)
        println("Hello $name")
    else
        println("Hi there!")
    // `return Unit` or `return` is optional
}
fun printHello2(name: String?) { /*...*/ }

// returning expression
fun triple(x: Int): Int = x * 3
fun triple2(x: Int) = x * 3

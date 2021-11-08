package course.kotlin.functions

import kotlin.text.Typography.times

class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

fun main() {
    val intFunction: (Int) -> Int = IntTransformer()
    val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int

    // function type with receiver
    fun String.myrepeat(times: Int): String {
        return this.repeat(times)
    }
    val repeatFun: String.(Int) -> String = { times ->
        this.repeat(times)
    }
    val twoParameters: (String, Int) -> String = repeatFun // OK
    
    fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }
    println(runTransformation(repeatFun)) // OK
//    println(runTransformation(myrepeat)) //  Not allowed
    println(repeatFun("a", 5))
    println("a".myrepeat(5))
//     println(myrepeat("a", 5)) // Not allowed

    // invoking function with receiver
    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // extension-like call
}

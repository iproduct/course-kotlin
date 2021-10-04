package course.kotlin

import kotlin.random.Random

enum class Bit {
    ZERO, ONE
}

fun main() {

    // if-else
    val a = 5
    val b = 10
    var max = a
    if (a < b) max = b

    // With else
    if (a > b) {
        max = a
    } else {
        max = b
    }

    // As expression
    val max2 = if (a > b) a else b

    val max3 = if (a > b) {
        println("Choose a")
        a
    } else {
        println("Choose b")
        b
    }

    // when
    val x = 3
    when (x) {
        1 -> println("x == 1")
        2 -> println("x == 2")
        else -> {
            println("x is neither 1 nor 2")
        }
    }

    fun getRandomBit(): Bit {
        return if (Random.nextBits(1) == 0) Bit.ZERO else Bit.ONE
    }

    val numericValue = when (getRandomBit()) {
        Bit.ZERO -> 0
        Bit.ONE -> 1
        // the 'else' clause is not required because all cases are covered
    }


    when (x) {
        0, 1 -> print("x == 0 or x == 1")
        else -> print("otherwise")
    }

    val s ="5"
    when (x) {
        s.toInt() -> print("s encodes x")
        else -> print("s does not encode x")
    }
    val validNumbers = 1..100
    when (x) {
        in 1..10 -> print("x is in the range")
        in validNumbers -> print("x is valid")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }

    fun hasPrefix(x: Any) = when (x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

//    when {
//        x.isOdd() -> print("x is odd")
//        y.isEven() -> print("y is even")
//        else -> print("x+y is odd")
//    }
//
//    fun GraalCompiler.Request.getBody() =
//        when (val response = executeRequest()) {
//            is Authenticator.Success -> response.body
//            is HttpError -> throw HttpException(response.status)
//        }

}

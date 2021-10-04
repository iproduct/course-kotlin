package course.kotlin

fun main() {
    // Arithmentic
    println(1 + 2)
    println(2_500_000_000L - 1L)
    println(3.14 * 2.71)
    println(10.0 / 3)

    val x = 5 / 2
//println(x == 2.5) // ERROR: Operator '==' cannot be applied to 'Int' and 'Double'
    println(x == 2)

    val x2 = 5L / 2
    println(x2 == 2L)

    val x3 = 5 / 2.toDouble()
    println(x3 == 2.5)

    // Bitwise
    val x4 = (1 shl 2) and 0x000FF00
}

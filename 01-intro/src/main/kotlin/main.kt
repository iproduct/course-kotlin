package course.kotlin

import java.awt.Rectangle
import java.util.*
import kotlin.text.*

fun main(args: Array<String>) {
    println("Hello World!: ${args.contentToString()}")
    println("sum = ${sum2(3, 5)}")
    printSum(35, 7)

    val a: Int = 1  // immediate assignment
    val b = 2   // `Int` type is inferred
    val c: Int  // Type required when no initializer is provided
    c = 3       // deferred assignment
    printSum(b, c)
    var x = 5 // `Int` type is inferred
    x += 1
    fun incrementX() {
        x += 1
    }
    incrementX()
    incrementX()
    println("x=$x")

    val r = Rectangle(12.toDouble(), 5.toDouble())
    println("Rectangle(${r.width}, ${r.height}) perimeter = ${r.perimeter}")

    var y = 1
    // simple name in template:
    val s1 = "y is $y"
    y = 2
    // arbitrary expression in template:
    val s2 = "${s1.replace("is", "was")}, but now is $y"
    println(s2)

    print("max of $b and $c is ${maxOf(b,c)}")
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

fun maxOf(a: Int, b: Int) = if (a > b) {
    print("Choose a\n")
    a
} else {
    print("Choose b\n")
    b
}

/**
 *  Base class for all shapes
 */
open class Shape

class Rectangle(var width: Double, var height: Double) : Shape() {
    var perimeter = (height + width) * 2
}


package course.kotlin

import java.awt.Shape
import java.util.function.IntBinaryOperator
import kotlin.reflect.typeOf

fun main(args: Array<String>) {
    println("Hello World!: ${args.contentToString()}")
    //    println("What's your name?")
//    val name = readLine()
//    println("Hello, $name!")
//    val sum = {a: Int, b:Int ->  a + b} // lambda
//    val sum = fun(a: Int, b:Int) = a + b // anonymous function
    fun sum2(a: Int = 0, b: Int = 0) = a + b
    val x = 1
    val y = 2
    println(sum2(b = 5))

    var s = 1
    val s2 = "s is $s"
    var s3 = 2
    val s4 = "${s2.replace("is", "was")}, but now is ${s3}"
    println(s4)

    val test = 1 to 100
    println(test.javaClass)
    open class Shape()
    class Rectangle(w: Double, h: Double) : Shape() {
        var s = w * h
    }

    val r = Rectangle(4.0, 2.0);
    println(r.s);

//    val n = 42;
//    val n = 42.toLong()
//    val n = (Long) 42;
//    val n = 42.0;
//    val n = Long.parseLong(42);
//    val n = Long.valueOf(42);

//    val a = mutableListOf(0, 1, 2)
//    val b = a.add(3, 4)
//    println(a)
//    println(b)

//    val a1 = listOf(0, 1, 2)
//    val b1 = a1.add(3)
//    println(a1)
//    println(b1)

    println(MyClass.staticMethod())

    val fruits = listOf("banana", "pomegranate", "avocado", "apple", "kiwifruit")
    val result = fruits
        .filter { it.contains("a") }
        .sortedBy { it }
        .take(3)
        .map { it.dropLast(3) }
        .foldRight("") { a, b -> a + b }
    println(result)

//    data class A(val x: String = "1", val y: String = "9", val z: String = "2")
//
//    val a = A("5")
//    val (u, v, w) = a.copy(z = "7")
//    print(u + v + w)

    class A(val value:Int) {
        fun Int.foo(): Int {
            fun String.myfunc(): Int = length
            val funLit = fun String.(): Int {
                val u = map{ it.digitToInt()}.filter { it > 5 }.fold(0, Int::plus)
                return u + myfunc()
            }
            return toString().funLit()
        }
        fun compute() = value.foo()
    }
    println(A(5297).compute())

    val myseq = sequence {
        yield(5)
        yieldAll(listOf(4, 1))
        yieldAll(generateSequence(7) { it * 2 })
    }
    println(myseq.take(5).toList())

    for (i in 9 downTo 1 step 2) print(i)
}

class MyClass {
    companion object {
        @JvmStatic
        fun staticMethod() = "Kotlin static method"
    }
}

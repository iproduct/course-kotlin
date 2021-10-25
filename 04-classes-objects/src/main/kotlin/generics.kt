package course.kotlin

class Box<T>(t: T) {
    var value = t
}

val box: Box<Int> = Box<Int>(1)

val box2 = Box(1) // 1 has type Int, so the compiler figures out that it is Box<Int>

// declaration site variance
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

// type projection
//class Array<T>(val size: Int) {
//    operator fun get(index: Int): T { /*...*/ return 1 as T }
//    operator fun set(index: Int, value: T) { /*...*/ }
//}

fun copy(from: Array<Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices) {
        to[i] = from[i]
    }
}
fun copy(from: Array<out Any>, to: Array<Any>) { ... }
fun fill(dest: Array<in String>, value: String) { ... }
fun main() {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    copy(ints, any)
    //   ^ type is Array<Int> but Array<Any> was expected
}



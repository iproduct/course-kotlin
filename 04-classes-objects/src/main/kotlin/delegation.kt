package course.kotlin.delegation

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(b: Base) : Base by b

fun main1() {
    val b = BaseImpl(10)
    Derived(b).print() // 10
}

// demo 2
interface Base2 {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl2(val x: Int) : Base2 {
    override fun printMessage() { print(x) }
    override fun printMessageLine() { println(x) }
}

class Derived2(b: Base2) : Base2 by b {
    override fun printMessage() { print("abc") }
}

fun main2() {
    val b = BaseImpl2(10)
    Derived2(b).printMessage() // abc
    Derived2(b).printMessageLine() //10
}

// overriden members not virtual
interface Base3 {
    val message: String
    fun print()
}

class BaseImpl3(val x: Int) : Base3 {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived3(b: Base3) : Base3 by b {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = BaseImpl3(10)
    val derived = Derived3(b)
    derived.print()
    println(derived.message)
}

package course.kotlin.interfaces

fun interface KRunnable {
    fun invoke()
}

fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

// Creating an instance of a class
val isEven = object : IntPredicate {
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}

// Creating an instance using lambda
val isEvenLambda = IntPredicate { it % 2 == 0 }

typealias Predicate<T> = (T) -> Boolean
typealias IntPredicate2 = Predicate<Int>

fun IntPredicate2.accept(n: Int):Boolean = this(n)

fun Collection<Int>.areAll(
    predicate: IntPredicate): Boolean {
    for (elem in this) {
        if (!predicate.accept(elem))
            return false
    }
    return true
}

fun Collection<Int>.areAll2(
    predicate: IntPredicate2): Boolean {
    for (elem in this) {
        if (!predicate.accept(elem))
            return false
    }
    return true
}

fun main() {
    println("Is 42 even? - ${isEven.accept(42)}")
    println("Is 42 even? - ${isEvenLambda.accept(42)}")
    val numbers = listOf(42, 12, 54, 32, 78)
    println("Are all numbers even in $numbers? - ${numbers.areAll({ it % 2 == 0 })}")
    println("Are all numbers even in $numbers? - ${numbers.areAll2({ it % 2 == 0 })}")
}

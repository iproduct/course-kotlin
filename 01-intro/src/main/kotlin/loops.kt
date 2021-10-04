package course.kotlin

import kotlin.random.Random

/*
 Create a POJO with getters, setters, `equals()`, `hashCode()`, `toString()` and `copy()` in a single line:
*/
data class Person(val name: String? = null, val email: String? = null, val age: Int? = 0)


fun main() {
    // for
    val collection = listOf(1,2,3,4, 5)
    for (item in collection) println(item)

    for (i in 1..3) {
        println(i)
    }
    for (i in 6 downTo 0 step 2) {
        println(i)
    }
    val array = IntArray(5){ it * it}
    for (i in array.indices) {
        println("$i -> ${array[i]}")
    }
    for ((index, value) in array.withIndex()) {
        println("the element at $index is $value")
    }

    // while
    var x = 5
    while (x > 0) {
        x--
    }

    fun retrieveData() = if (Random.nextBoolean()) "data ..." else null

    do {
        val y = retrieveData()
    } while (y != null) // y is visible here!

    // return, break, continue
    val person = Person("Ivan Petrov")
    val s = person.name ?: return
    println("Person name: $s")

    loop@ for (i in 1..100) {
        for (j in 1..100) {
            print(j)
            if (j == 3) break@loop
        }
    }
    println()

    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) return@lit// local return to the caller of the lambda - the forEach loop
            print(it)
        }
        println(" done with explicit label")
    }
    foo()

    fun bar() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // local return to the caller of the lambda - the forEach loop
            print(it)
        }
        println(" done with implicit label")
    }
    bar()

    // simulate break
    fun baz() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop // non-local return from the lambda passed to run
                print(it)
            }
        }
        println(" done with nested loop")
    }
    baz()

}

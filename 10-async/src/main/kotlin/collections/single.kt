package collections

fun main() {
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    println(numbers.first { it.length > 3 })
    println(numbers.last { it.startsWith("f") })

    println(numbers.firstOrNull { it.length > 6 })
}

package collections

fun main() {
    val numbers = listOf("one", "two", "three", "four", "five")

    println(numbers.groupBy { it.first().uppercase() })
    println(numbers.groupBy(keySelector = { it.first() }, valueTransform = { it.uppercase() }))

    val numbers2 = listOf("one", "two", "three", "four", "five", "six")
    println(numbers2.groupingBy { it.first() }.eachCount())
}

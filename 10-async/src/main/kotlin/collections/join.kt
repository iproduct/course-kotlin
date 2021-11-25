package collections

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.joinToString(separator = " | ", prefix = "start: ", postfix = ": end"))

    val listString = StringBuffer("The list of numbers: ")
    numbers.joinTo(listString)
    println(listString)

    val numbers2 = (1..100).toList()
    println(numbers2.joinToString(limit = 10, truncated = "<...>"))

    val numbers3 = listOf("one", "two", "three", "four")
    println(numbers3.joinToString { "Element: ${it.uppercase()}"})
}

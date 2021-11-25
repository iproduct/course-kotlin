package collections

fun main() {
    val numbers = listOf("one", "two", "three", "four", "five")
    println(numbers.windowed(3))

    val numbers2 = (1..10).toList()
    println(numbers2.windowed(3, step = 2, partialWindows = true))
    println(numbers2.windowed(3) { it.sum() })

    println(numbers.zipWithNext())
    println(numbers.zipWithNext() { s1, s2 -> s1.length > s2.length})
}

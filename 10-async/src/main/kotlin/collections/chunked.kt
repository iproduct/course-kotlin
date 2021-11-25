package collections

fun main() {
    val numbers = (0..13).toList()
    println(numbers.chunked(3))

    println(numbers.chunked(3) { it.sum() })  // `it` is a chunk of the original collection
}

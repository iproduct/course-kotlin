package flow

private fun foo(): List<Int> = listOf(1, 2, 3, 4 ,5)

fun main() {
    foo().forEach { value -> println(value) }
}

package course.kotlin.functions

fun <T, R> Collection<T>.reduce(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

fun main() {
    val items = listOf(1, 2, 3, 4, 5)
    println(items)
    // Lambdas are code blocks enclosed in curly braces.
    items.fold(0) {
        // When a lambda has parameters, they go first, followed by '->'
            acc: Int, i: Int ->
                print("acc = $acc, i = $i, ")
                val result = acc + i
                println("result = $result")
                // The last expression in a lambda is considered the return value:
                result
    }

    // Parameter types in a lambda are optional if they can be inferred:
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })
    println(joinedToString)

    // Function references can also be used for higher-order function calls:
    val product = items.fold(1, Int::times)
    println(product)
}

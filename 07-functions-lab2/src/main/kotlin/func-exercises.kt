fun foo(
    bar: Int = 1,
    baz: Int,
    transform: (Int, Int) -> String = { x, y -> "$x, $y" },
): String = transform(bar, baz)

infix fun <T> List<T>.join(sep:String): String {
    return joinToString(sep)
}

fun <T> asList(vararg args: T): List<T> {
//    args[0] = 42 as T // newer works
    val result = mutableListOf<T>()
    for (e in args) {
        result.add(e)
    }
    return result
}



fun main() {
    println(foo(baz = 42) { x, y -> "${x * y}" })
    val fruits = asList("Apple", "Banana", "Cherry", "Watermellon", "Pineapple", "Plum")
//    println(join(" | ", *fruits.toTypedArray() ))
    println(fruits join " | ")
}

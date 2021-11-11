package course.kotlin.functions

fun <T> max(collection: Collection<T>, comparatorFn: (T, T) -> Int): T? {
    var maxElem: T? = null
    for (e in collection) {
        if (maxElem == null) {
            maxElem = e
        } else if (e != null && comparatorFn(e, maxElem) > 0)
            maxElem = e
    }
    return maxElem
}

fun compare(a: String, b: String): Int = a.length - b.length

fun main() {
    val strings = listOf("Orange", "Banana", "Pineapple", "Papaya", "Apple", "Plum")
    println(max(strings) { a, b -> a.length - b.length })

//    val sum1: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
//    val sum = { x: Int, y: Int -> x + y }
//    val ints = listOf(1, 2, 3)
//    val product = ints.fold(1) { acc, e -> acc * e }
//    run { println("...") }
//    ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'
//
//    ints.filter {
//        val shouldFilter = it > 0
//        shouldFilter
//    }
//
//    ints.filter {
//        val shouldFilter = it > 0
//        return@filter shouldFilter
//    }
//
//    strings.filter { it.length == 5 }.sortedBy { it }.map { it.uppercase() }
//
//    val map = mapOf(1 to "x", 2 to "y", 3 to "z")
//    map.forEach { _, value -> println("$value!") }
//
//    // destructuring
//    map.mapValues { entry -> "${entry.value}!" }
//    map.mapValues { (key, value) -> "$value!" }
//    map.mapValues { (_, value) -> "$value!" }
//    map.mapValues { (_, value): Map.Entry<Int, String> -> "$value!" }
//    map.mapValues { (_, value: String) -> "$value!" }
//
//    //anonimous functions
//    fun(x: Int, y: Int): Int = x + y
//    fun(x: Int, y: Int): Int {
//        return x + y
//    }
//    ints.filter(fun(item) = item > 0)
//
//    //with receiver
//    val sum2: Int.(Int) -> Int = { other -> plus(other) }
//    val sum3 = fun Int.(other: Int): Int = this + other
}

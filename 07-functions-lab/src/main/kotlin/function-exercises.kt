package course.kotlin

fun foo(
    bar: Int = 1,
    baz: Int,
    qux: (bar: Int, baz: Int) -> Unit = { x, y -> print("$x, $y\n") },
) {
    qux(bar, baz)
}

fun variadic_function(vararg strings: String) = strings.joinToString(separator = " | ")

fun <T> asList(vararg elems: T): List<T> {
//    elems[0] = 42 as T // can never succeed
    val result = ArrayList<T>()
    elems.reverse()
    for (elem in elems) {
        result.add(elem)
    }
    return result //listOf<Nothing>() // emptyList()
}

//infix fun <T> List<T>.filter(criteria: (T) -> Boolean): List<T> {
//    val result = ArrayList<T>()
//    for (elem in this) {
//        if(criteria(elem)) {
//            result.add(elem)
//        }
//    }
//    return result
//}

infix fun <T> List<T>.myfilter(criteria: (T) -> Boolean) = filter(criteria)


fun <T, R> Collection<T>.myreduce(initial: R, reducer: (R, T) -> R): R =
    myreduce(initial, { acc, elem, index -> reducer(acc, elem) })

fun <T, R> Collection<T>.myreduce(initial: R, reducer: (R, T, Int) -> R): R {
    var acc = initial
    var i = 0
    for (elem in this) {
        acc = reducer(acc, elem, i)
        i++
    }
    return acc;
}

fun main() {
    foo(1, 2) { x, y -> print("hi: $x, $y\n") }
    foo(42, 108) { x, y -> print("hi: $x, $y\n") }
    foo(42, 108)
    foo(baz = 42)
    val fruits = listOf("Apple", "Banana", "Lemon", "Orange", "Cherry", "Pineapple")
    println(variadic_function(*fruits.toTypedArray()))
    println(asList(*fruits.toTypedArray()))
    println(fruits myfilter { it.contains('a', ignoreCase = true) })
    println(fruits.myreduce("") { acc, elem, index -> acc + (if (index > 0) " | " else "") + elem })
    println(fruits.myreduce("") { acc, elem -> acc + " | " + elem })
    println(fruits.myreduce(1) { acc, elem -> acc * elem.length })

    val intNums = (1 .. 5).toList()
    println(intNums.myreduce(1) { acc, elem -> acc * elem })
    println(fruits.myreduce(1) { acc, elem -> acc * elem.length })
}

package course.kotlin.functions

fun reformat(
    str: String,
    normalizeCase: Boolean = true,
    upperCaseFirstLetter: Boolean = true,
    divideByCamelHumps: Boolean = false,
    wordSeparator: Char = ' ',
) { /*...*/ }

// varargs
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

fun main() {
    reformat(
        "String!",
        false,
        upperCaseFirstLetter = false,
        divideByCamelHumps = true,
        '_'
    )
    reformat("This is a long String!")
    reformat("This is a short String!", upperCaseFirstLetter = false, wordSeparator = '_')

    //varargs
    fun foo(vararg strings: String) { /*...*/ }
    foo(strings = *arrayOf("a", "b", "c"))

    val a = arrayOf(1, 2, 3)
    val list = asList(-1, 0, *a, 4)
    val a2 = intArrayOf(1, 2, 3) // IntArray is a primitive type array
    val list2 = asList(-1, 0, *a2.toTypedArray(), 4)
}

package course.kotlin

import java.io.File

fun main() {
    fun f(obj: Any): Unit {
        if (obj is String && obj.length > 0) {
            println("$obj: ${obj.length}")
        }

        if (obj !is String) { // same as !(obj is String)
            println("Not a String")
        } else {
            println(obj.length)
        }
    }
//    f("abc")

    fun g(x: Any) {
        when (x) {
            is Int -> println(x + 1)
            is String -> println(x.length + 1)
            is IntArray -> println(x.sum())
        }
    }

    fun g2(x: Any) = when (x) {
        is Int -> "${x + 1}"
        is String -> "${x.length + 1}"
        is IntArray -> "${x.sum()}"
        else -> "Not recognized"
    }

    fun g3(x: Any) = if (x is Int) "${x + 1}"
    else if (x is String) "${x.length + 1}"
    else if (x is IntArray) "${x.sum()}"
    else "Not recognized"

//    println(g2(1))
//    println(g2("abc"))
    println(g2(IntArray(5) { it }))
    println(g2(Shape()))

    fun h(y: Any?) {
//        val x = y as String
//        val x: String? = y as String?
        val x: String? = y as? String
        print(x + " ")
    }
    h("abc")
    h(12)
    h(null)

//    fun readDictionary(file: File): Map<String, *> = file.inputStream().use {
//        TODO("Read a mapping of strings to arbitrary elements.")
//    }
//
//    // We saved a map with `Int`s into this file
//    val intsFile = File("ints.dictionary")
//
//    // Warning: Unchecked cast: `Map<String, *>` to `Map<String, Int>`
//    val intsDictionary: Map<String, Int> = readDictionary(intsFile) as Map<String, Int>
}

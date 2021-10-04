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
    f("abc")

    fun g(x: Any) {
        when (x) {
            is Int -> println(x + 1)
            is String -> println(x.length + 1)
            is IntArray -> println(x.sum())
        }
    }
    g(1)
    g("abc")
    g(IntArray(5) { it })

    fun h(y: Any?) {
//        val x: String = y as String
//        val x: String? = y as String?
        val x: String? = y as? String
        println(x)
    }
    h(12)

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

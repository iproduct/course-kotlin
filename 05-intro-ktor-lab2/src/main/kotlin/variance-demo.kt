package course.kotlin.variance

class Container<out T>(val value: T, var name: String) {
//    fun compare(other: T): Int = 42
}

fun main() {
    val a = ArrayList<Container<*>>()
    a.add(Container(42, "IntContainer"))
    val b = a.get(0)
    b.name = "new name"
}

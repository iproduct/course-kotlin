data class Post(val title: String, val content: String, var id: Long?)

fun main() {
    var x = 1
    fun incrementX() {
        x++
    }
    incrementX()
    incrementX()
    println("x = $x")

    open class Shape

    fun Shape.draw() = println("Inside draw()")

    val s1 = Shape()
    s1.draw()

    class Rectangle(var width: Double = 0.0, var height: Double = 0.0) : Shape() {
        val perimeter
            get() = (width + height) * 2

        override fun toString(): String {
            return "Rectangle($width, $height)"
        }
    }

    val r1 = Rectangle(3.0, 4.0)
    r1.width = 5.0
    println("$r1 perimeter is ${r1.perimeter}")

    val posts = listOf(
            Post("New in Kotlin", "MPP is new ...", 1L),
            Post( "Kotlin compared to Java", "Many extras added ...", 2L),
            Post("Coroutines and Channels in Kotlin", "Concurrent programming is made easier ...", 3L),
    )

    posts.filter { it.title.contains("Kotlin") }
            .map { "${it.id}: ${it.title} - ${it.content}" }
            .forEach{ println(it) }
}

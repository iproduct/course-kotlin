fun main(args: Array<String>) {
    println("Hello World!: ${args.joinToString()}")
//    println("Your name:")
//    val name = readLine()
//    println("Hello $name")
    println("Sum 3 + 5 = ${sum(3, 5)}")
    var x = 5
    x++
    fun incrementX() {
        x++
    }
    incrementX()
    println("x = $x")
    val r = Rectangle(3.0, 5.0)
    println("Perimeter of $r = ${r.perimeter}, Area of $r = ${r.area}")

    // Arrays
//    val a = arrayOf(1, 2, 3, 4, 5)
    val a = Array(10) { it + 1 }
    val squares = a.map { it * it }.map { it.toString() }.filter { it.length > 1 }
    println(squares)
    val sum = a.fold(0, {acc, v -> acc + v})
    println("Sum of ${a.asList()} = $sum")
}

fun sum(a: Int = 0, b: Int = 0) = a + b

class Rectangle(var width: Double, var height: Double) {
    var perimeter = (width + height) * 2
    var area = width * height
    override fun toString(): String {
        return "Rectangle($width, $height)"
    }
}

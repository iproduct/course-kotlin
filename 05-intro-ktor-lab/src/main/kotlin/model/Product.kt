package course.kotlin.model

data class ProductData (val name: String, val price: Double)
data class Product (val id: Int, val name: String, val price: Double)

fun main() {
    val p = ProductData("Laptop", 2500.0)
    val pDiscounted = p.copy(price = p.price * 0.75)
    val (name, price) = pDiscounted
    println(p.component1())
    println(p.component2())
    println(name)
    println(price)

}

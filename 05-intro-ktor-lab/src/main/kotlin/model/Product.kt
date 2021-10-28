package course.kotlin.model

import java.time.LocalDateTime

//data class ProductData (val name: String, val price: Double)

data class Product(
    override var id: Int,
    val name: String,
    val price: Double,
    val created: LocalDateTime = LocalDateTime.now(),
) : Identifiable<Int> {

    constructor(
        name: String,
        price: Double,
        created: LocalDateTime = LocalDateTime.now()
    ) : this(0, name, price, created)

}

//fun main() {
//    val p = ProductData("Laptop", 2500.0)
//    val pDiscounted = p.copy(price = p.price * 0.75)
//    val (name, price) = pDiscounted
//    println(p.component1())
//    println(p.component2())
//    println(name)
//    println(price)
//
//}

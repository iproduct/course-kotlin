package course.kotlin.model

import java.time.LocalDateTime

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val created: LocalDateTime = LocalDateTime.now(),
) {

    constructor(
        name: String,
        price: Double,
        created: LocalDateTime = LocalDateTime.now()
    ) : this(0, name, price, created)

}

fun main() {
    val now = LocalDateTime.now()
    val p1 = Product(1, "A", 1.0, now)
    val p2 = Product(2, "A", 1.0, now)
    println(p1 == p2)
}

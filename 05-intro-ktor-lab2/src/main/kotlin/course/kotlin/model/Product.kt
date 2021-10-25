package course.kotlin.model

import java.time.LocalDateTime
import kotlin.reflect.KCallable

interface Identifiable<K> {
    var id: K
}

//interface Copyable<T> {
//    fun copy() : KCallable<T>
//}

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

fun main() {
    val now = LocalDateTime.now()
    val p1 = Product(1, "A", 1.0, now)
    val p3 = p1.copy(id = 5)
    val p2 = Product(2, "A", 1.0, now)
    println(p1 == p2)
}

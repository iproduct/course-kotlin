package intro

import java.time.LocalDateTime

data class Product(val name: String, val price: Double)
class Order(val number: Int, val products: List<Product>, val date: LocalDateTime = LocalDateTime.now() ){
    fun calculateTotal(): Double {
        return products
            .map{it.price}
            .reduce { acc, prod  ->  acc + prod }
        }
    }


//// Get rid of those pesky NullPointerExceptions
////var output: String
////output = null   // Compilation error
////
////// Kotlin protects you from mistakenly operating on nullable types
////val name: String? = null    // Nullable type
////println(name.length())      // Compilation error

// And if you check a type is right, the compiler will auto-cast it for you
fun calculateTotal(obj: Any): Double? {
    if (obj is Order)
        return obj.calculateTotal()
    return 0.0
}

fun main() {
    val products = listOf(Product("Keyboard", 27.5),  Product("Mouse", 17.1))
    val order = Order(1, products)
    println(calculateTotal((order))); // => 44.6
}

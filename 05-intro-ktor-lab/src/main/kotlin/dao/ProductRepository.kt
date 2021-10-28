package course.kotlin.dao

import course.kotlin.model.Product
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class ProductRepository(): ConcurrentHashMap<Int, Product>() {
    private val idSequence = AtomicInteger()
    constructor(products :List<Product>): this() {
       products.forEach(::addProduct)
    }

    fun addProduct(pd : Product): Product {
        val p = Product(idSequence.incrementAndGet(), pd.name, pd.price)
        put(p.id, p)
        return p
    }
}

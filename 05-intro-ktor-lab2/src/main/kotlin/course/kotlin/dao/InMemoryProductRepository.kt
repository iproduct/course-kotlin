package course.kotlin.dao

import course.kotlin.model.Product
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

internal class InMemoryProductRepository(products: Collection<Product> = emptyList()) : ProductRepository {
    private val items: MutableMap<Int, Product> = ConcurrentHashMap()
    private val idSequence = AtomicInteger()

    init {
        products.forEach { create(it)}
    }

    override fun findAll(): Collection<Product> = items.values
    override fun findById(id: Int): Product? = items[id]

    override fun create(product: Product): Product {
        val newProduct = product.copy(id = idSequence.incrementAndGet())
        items[newProduct.id] = newProduct
        return newProduct
    }

    override fun update(product: Product): Product {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Product? {
        TODO("Not yet implemented")
    }

}

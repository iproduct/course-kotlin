package course.kotlin.dao

import course.kotlin.model.Product
import java.util.concurrent.ConcurrentHashMap

internal class InMemoryProductRepository(products: Collection<Product> = emptyList()) : ProductRepository {
    private val items: MutableMap<Int, Product> = ConcurrentHashMap()

    init {
        products.forEach { items[it.id] = it }
    }

    override fun findAll(): Collection<Product> = items.values

    override fun findById(): Product? {
        TODO("Not yet implemented")
    }

    override fun create(product: Product): Product {
        TODO("Not yet implemented")
    }

    override fun update(product: Product): Product {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Product? {
        TODO("Not yet implemented")
    }

}

package course.kotlin.dao

import course.kotlin.model.Product

interface ProductRepository {
    fun findAll(): Collection<Product>
    fun findById(id: Int): Product?
    fun create(product: Product): Product
    fun update(product: Product): Product
    fun deleteById(id: Int): Product?
}

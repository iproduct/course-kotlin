package course.kotlin.dao

import course.kotlin.model.Product

interface Repository<K, T> {
    fun findAll(): Collection<T>
    fun findById(id: K): T?
    fun create(product: T): T
    fun update(product: T): T
    fun deleteById(id: K): T?
}

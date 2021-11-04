package course.kotlin.variance.course.kotlin.dao

interface Repository<K, V> {
    fun findAll(): Collection<V>
    fun findById(id: K): V?
    fun create(item: V): V
    fun update(item: V): V
    fun deleteById(id: K): V?
    fun count(): Int
}

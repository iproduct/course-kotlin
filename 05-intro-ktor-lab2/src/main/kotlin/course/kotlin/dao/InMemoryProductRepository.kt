package course.kotlin.dao

import course.kotlin.model.Identifiable
import io.ktor.utils.io.*
import java.util.concurrent.ConcurrentHashMap

interface IdGenerator<K> {
    fun getNextId(): K
}

class InMemoryRepository<K, T : Identifiable<K>>(
    initialItems: List<T> = emptyList(),
    val generator: IdGenerator<K>
) : Repository<K, T> {
    //        where T : Identifiable<K>, T : Copyable<K, T>  {
    private val items: MutableMap<K, T> = ConcurrentHashMap()

    init {
        initialItems.forEach { create(it) }
    }

    override fun findAll(): Collection<T> = items.values
    override fun findById(id: K): T? = items[id]

    override fun create(item: T): T {
        item.id = generator.getNextId()
        items[item.id] = item
        return item
    }

    override fun update(product: T): T {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: K): T? {
        TODO("Not yet implemented")
    }

}

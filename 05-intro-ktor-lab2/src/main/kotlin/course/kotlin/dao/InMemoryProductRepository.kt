package course.kotlin.dao

import course.kotlin.model.Identifiable
import course.kotlin.variance.course.kotlin.dao.IdGenerator
import course.kotlin.variance.course.kotlin.dao.Repository
import io.ktor.utils.io.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryRepository<K, V: Identifiable<K>>(
    val generator: IdGenerator<K>,
    initialItems: List<V> = emptyList()
) : Repository<K, V> {
    //        where T : Identifiable<K>, T : Copyable<K, T>  {
    private val items: MutableMap<K, V> = ConcurrentHashMap()

    init {
        initialItems.forEach { create(it) }
    }

    override fun findAll(): Collection<V> = items.values
    override fun findById(id: K): V? = items[id]

    override fun create(item: V): V {
        item.id = generator.getNextId()
        items[item.id] = item
        return item
    }

    override fun update(item: V): V {
        items[item.id] = item
        return item
    }

    override fun deleteById(id: K): V? {
        return items.remove(id)
    }

    override fun count(): Int = items.size
}

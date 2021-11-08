package course.kotlin.dao

import course.kotlin.model.IdGenerator
import course.kotlin.model.Identifiable
import java.sql.SQLException
import java.util.concurrent.ConcurrentHashMap

class InMemoryRepository<K, V>(
    val idGen: IdGenerator<K>,
    initialItems: List<V> = emptyList()
) : Repository<K, V> where  V : Identifiable<K>, K : Comparable<K>{
    private val items: MutableMap<K, V> = ConcurrentHashMap()

    init {
        initialItems.forEach(::create) //{create(it)}
    }

    override fun findAll(): Collection<V> = items.values

    override fun findById(id: K): V? =  items[id] //throw SQLException("Database crashed!")

    override fun create(item: V): V {
        item.id = idGen.nextId()
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

}

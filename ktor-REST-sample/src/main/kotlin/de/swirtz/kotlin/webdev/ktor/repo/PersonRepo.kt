package de.swirtz.kotlin.webdev.ktor.repo

import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.atomic.AtomicInteger

object PersonRepo {

    private val idCounter = AtomicInteger()
//    private val persons = mutableSetOf<Person>()
    private val persons = ConcurrentSkipListSet<Person>()

//    private fun <R> personsSynchronized(block: () -> R): R = synchronized(persons) {
//        return block()
//    }

    fun add(p: Person): Person {
        if (persons.contains(p)) {
            persons.find { it == p }!!
        }
        p.id = idCounter.incrementAndGet()
        persons.add(p)
        return p
    }

    fun get(id: String): Person {
        return persons.find { it.id.toString() == id } ?: throw IllegalArgumentException("No entitiy found for $id")
    }

    fun get(id: Int): Person {
        return get(id.toString())
    }

    fun getAll(): List<Person> { return persons.toList() }

    fun remove(p: Person): Boolean  {
        if (!persons.contains(p)) {
            throw IllegalArgumentException("Person not stored in repo.")
        }
        return persons.remove(p)
    }

    fun remove(id: String): Boolean { return persons.remove(get(id)) }

    fun remove(id: Int): Boolean { return persons.remove(get(id.toString())) }

    fun clear() { persons.clear() }

}

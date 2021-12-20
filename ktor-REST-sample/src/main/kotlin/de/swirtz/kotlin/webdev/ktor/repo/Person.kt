package de.swirtz.kotlin.webdev.ktor.repo

data class Person(val name: String, val age: Int): Comparable<Person>{
    var id: Int? = null
    override fun compareTo(other: Person): Int {
        return (id?: 0) - (other.id ?: 0)
    }

}

package course.kotlin.model

interface IdGenerator<K> {
    fun nextId(): K
}

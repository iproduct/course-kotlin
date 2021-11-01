package course.kotlin.model

fun interface IdGenerator<K> {
    fun nextId(): K
}

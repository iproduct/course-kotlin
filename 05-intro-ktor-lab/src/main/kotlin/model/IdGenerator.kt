package course.kotlin.model

//SAM
fun interface IdGenerator<K> {
    fun nextId(): K
}

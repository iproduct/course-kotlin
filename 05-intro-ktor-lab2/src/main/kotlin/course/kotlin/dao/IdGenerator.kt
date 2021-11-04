package course.kotlin.variance.course.kotlin.dao

fun interface IdGenerator<K> {
    fun getNextId(): K
}

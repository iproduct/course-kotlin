package course.kotlin.fielddelegate

import course.kotlin.weakidentityhashmap.WeakIdentityHashMap
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KProperty

class FieldPropertyDelegate<R, T: Any>(val initializer: R.()->T = {throw IllegalStateException("Not initialized.")}) {
    private var map = WeakIdentityHashMap<R, T>()
    operator fun getValue(thisRef: R, property: KProperty<*>): T =
        map[thisRef] ?: setValue(thisRef, property, initializer(thisRef))
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T {
        map[thisRef] = value
        return value
    }
}

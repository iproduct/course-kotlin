package course.kotlin.fielddelegate

import course.kotlin.weakidentityhashmap.WeakIdentityHashMap
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KProperty



class SynchronizedFieldPropertyDelegate<R, T: Any>(val initializer: R.()->T = {throw IllegalStateException("Not initialized.")}) {
    private var map = WeakIdentityHashMap<R, T>()
    operator fun getValue(thisRef: R, property: KProperty<*>): T = synchronized(map) {
        println(property.name)
        map[thisRef] ?: setValue(thisRef, property, initializer(thisRef))
    }
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T {
        synchronized(map) {
            map[thisRef] = value
        }
        return value
    }
}

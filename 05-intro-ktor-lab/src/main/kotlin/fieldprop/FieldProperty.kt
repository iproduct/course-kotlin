package course.kotlin.fieldprop

import course.kotlin.WeakIdentityHashMap
import course.kotlin.model.Product
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.reflect.KProperty

class FieldProperty<R, T>(val initializer: R.() -> T ={throw IllegalStateException("Not initialized.")}) {
    private val map = WeakIdentityHashMap<R,T>()
    operator fun getValue(thisRef: R, property: KProperty<*>): T  =
        map[thisRef] ?: setValue(thisRef, property, initializer(thisRef))
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T {
        map[thisRef] = value
        return value
    }
}

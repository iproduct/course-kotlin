package course.kotlin.fielddelegate

import java.lang.IllegalStateException
import kotlin.reflect.KProperty

class FieldPropertyDelegate<R, T: Any>(val initializer: R.()->T = {throw IllegalStateException("Not initialized.")}) {
    private lateinit var prop: T
    operator fun getValue(thisRef: R, property: KProperty<*>): T =
        if (::prop.isInitialized) prop
        else setValue(thisRef, property, initializer(thisRef))
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T {
        prop = value
        return value
    }
}

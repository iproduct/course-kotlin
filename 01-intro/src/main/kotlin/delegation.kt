package course.kotlin

import kotlin.reflect.KProperty

class Delegate {
    var _value: String = ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me: $_value"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        _value =value
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Example {
    var prop: String by Delegate()
}

// top level property
var topProp: String by Delegate()

fun main() {
    val e = Example()
    e.prop = "NEW_VALUE"
    println(e.prop)

    // top prop
    topProp = "TOP PROP NEW VALUE"
    println(topProp)
}

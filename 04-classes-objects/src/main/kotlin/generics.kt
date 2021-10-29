package course.kotlin

class Box<T>(t: T) {
    var value = t
}

val box: Box<Int> = Box<Int>(1)

val box2 = Box(1) // 1 has type Int, so the compiler figures out that it is Box<Int>

// declaration site variance
class Source<out T> {
    fun nextT(): T = 1 as T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    objects.nextT()
//    val objects: Source<Any> = Source() // This is OK, since T is an out-parameter
//    val newStrings: Source<String> = objects
    // ...
}

//interface Comparable2<in T> {
//    operator fun compareTo(other: T): Int
//}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    var y: Comparable<Number> = x // OK!
    var z: Comparable<Int> = 1
    z = y
}

// type projection
//class Array<T>(val size: Int) {
//    operator fun get(index: Int): T { /*...*/ return 1 as T }
//    operator fun set(index: Int, value: T) { /*...*/ }
//}

//fun copy(from: Array<out Any>, to: Array<Any>) {
//    assert(from.size == to.size)
//    for (i in from.indices) {
//        to[i] = from[i]
////        from[i] =  to[i] // Error
//    }
//}

fun copy(from: Array<out Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices) {
        to[i] = from[i]
    }
}
//fun fill(dest: Array<in String>, value: String) { ... }

// generic functions
fun <T> singletonList(item: T): List<T> {
    return emptyList()
}

fun <T> T.basicToString(): String { // extension function
    return ""
}

// generic constraints
fun <T : Comparable<T>> sort(list: List<T>) {  /*...*/
}

fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

// exercises
open class Product2(val name: String = "", val price: Double = 0.0, var id: Int? = null) {
    init {
        println("In Product init ...")
    }

    val calculateVat get() = price * 0.2
}

class Mobile : Product2()
class Consumable(name: String, price: Double) : Product2(name, price)
class Repository<in T : Product2> {
    val items: MutableList<in T> = mutableListOf()
    fun addProduct(p: T) {
        items.add(p)
//        val p: T =  items.get(0)
//        println(p.calculateVat)
    }

    fun addProducts(other: List<T>) {
        items.addAll(other)
//        val p: T =  items.get(0)
//        println(p.calculateVat)
    }
}

fun main() {
//    val repo = Repository<Product2>()
//    val consumable = Consumable("Print documents service", 5.0)
//    val product = Product2("Print documents service", 5.0)
//    val mobiles = listOf(Mobile(), Mobile())
//    repo.addProducts(mobiles)

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any :Array<Any> = arrayOf( "", 2, 3.0 )
    copy(ints, any)
    println(any.joinToString(", ") { it.toString() })
    //   ^ type is Array<Int> but Array<Any> was expected
//
//    // generic functions
//    val l = singletonList<Int>(1)
//    val l2 = singletonList(1)
//
//    // generic constraints
//    sort(listOf(1, 2, 3)) // OK. Int is a subtype of Comparable<Int>
////    sort(listOf(HashMap<Int, String>())) // Error: HashMap<Int, String> is not a subtype of Comparable<HashMap<Int, String>>
}




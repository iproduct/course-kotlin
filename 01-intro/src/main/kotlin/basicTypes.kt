package course.kotlin

@ExperimentalUnsignedTypes
fun main() {
//    val one = 1 // Int
//    val threeBillion = 3000000000 // Long
//    val oneLong = 1L // Long
//    val oneByte: Byte = 1
//
//    val pi = 3.14 // Double
////    val two: Double = 1 // Error: type mismatch
//    val twoDouble = 1.0 // Double
//    val e = 2.7182818284 // Double
//    val eFloat = 2.7182818284f // Float, actual value is 2.7182817
//
//    fun printDouble(d: Double) {
//        println(d)
//    }
//
//    val i = 1
//    val d = 1.0
//    val f = 1.0f
//
//    printDouble(d)
////    printDouble(i) // Error: Type mismatch
////    printDouble(f) // Error: Type mismatch
//
//    val oneMillion = 1_000_000
//    val creditCardNumber = 1234_5678_9012_3456L
//    val socialSecurityNumber = 999_99_9999L
//    val hexBytes = 0xFF_EC_DE_5E
//    val bytes = 0b11010010_01101001_10010100_10010010
//
//    val a: Int = 100
//    val boxedA: Int? = a
//    val anotherBoxedA: Int? = a
//
//    val b: Int = 10000
//    val boxedB: Int? = b
//    val anotherBoxedB: Int? = b
//
//    println(boxedA === anotherBoxedA) // true
//    println(boxedB === anotherBoxedB) // false
//
//    println(b == b) // Prints 'true'
//    println(boxedB == anotherBoxedB) // Prints 'true'
//
//    // Hypothetical code, does not actually compile:
//    val x: Int? = 1 // A boxed Int (java.lang.Integer)
////    val y: Long? = x // implicit conversion yields a boxed Long (java.lang.Long)
////    print(x == y) // Surprise! This prints "false" as Long's equals() checks whether the other is Long as well
//
//    val i2: Int = 1000000 // OK, literals are checked statically
//// val i: Int = b2 // ERROR
//    val b2: Byte = i2.toByte()
//    println("toByte() = $b2")
//
//    val l = 1L + 3 // Long + Int => Long
//
//    val l4 = 5L / 2
//    println("5L / 2 = $l4")
//
//    // bit operations
//    val l5 = (1L shl 7) and 0x0000FF00L
//    println("(1 shl 7) and 0x0000FF00 = $l5")

//    // range and progression
//    for (i in 9 downTo 1 step 2) {
//        println(i)
//    }

//    // Unsigned integer types and arrays
//    val ub: UByte = 1u  // UByte, expected type provided
//    val us: UShort = 1u // UShort, expected type provided
//    val ul: ULong = 1UL  // ULong, expected type provided
//
//    val a1 = 42u // UInt: no expected type provided, constant fits in UInt
//    val a2 = 0xFFFF_FFFF_FFFFu // ULong: no expected type provided, constant doesn't fit in UInt
//    val a3 = 1UL // ULong, even though no expected type provided and constant fits into UInt
//
//    val u: UIntProgression = 1U..10U step 2
//    val ua: UIntArray = UIntArray(10)
//    var j = 0
//    for (e in u) {
//        ua[j] = e
//        j++
//    }
//
//    for ((ind, v) in ua.withIndex()) {
//        print("$ind - > $v, ") // 1, 3, 5, 7, 9, 0, 0, 0, 0, 0,
//    }
//    println()
//
//    for (ch in "abcdef") {
//        print("$ch, ")
//    }

//    // Boolean
//    val myTrue: Boolean = true
//    val myFalse: Boolean = false
//    val boolNull: Boolean? = null
//
//    println(myTrue || myFalse)
//    println(myTrue && myFalse)
//    println(!myTrue)
//
//    // Char
//    val aChar: Char = 'a'
//
//    println(aChar)
//    println('\n') //prints an extra newline character
//    println('\uFF00')
//
//    // String
//    val s = "123"
//    for ((i, c) in s.withIndex()) {
//        println("$i -> ${c.toString().toInt()}")
//    }
//
//    println("12345".toInt())
//
//
//    val str = "abcd"
//    val str1 = str.toUpperCase()
//    println(str1) // Create and print a new String object
//    println(str === str1) // the original string remains the same
//    val s2 = "abc" + 1
//    println(s2 + "def")

    // Arrays
//    abstract class Array<T> private constructor() {
//        val size: Int
//        operator fun get(index: Int): T
//        operator fun set(index: Int, value: T): Unit
//
//        operator fun iterator(): Iterator<T>
//        // ...
//    }
    val a4 = arrayOf(1, 2, 3)
    val a5: Array<String?> = arrayOfNulls(3)
    val asc = Array(5) { (it * it).toString() }
    a4.forEach { println(it) }
    a5.forEach { println(it) }
    asc.forEach { println(it) }
    val x2: IntArray = intArrayOf(1, 2, 3)
    x2[0] = x2[1] + x2[2]
    println(x2.asList())

    val arr1 = IntArray(5)
    val arr2 = IntArray(5) { 42 }
    var arr3 = IntArray(20) { it * 2 }
    arr1.forEach { println(it) }
    arr2.forEach { println(it) }
    arr3.filter { it % 3 == 0 }.forEach { print("$it, ") }
    println()

//    val str2 = """
//        >First line \n
//        >Sevcond line
//        >third line
//        >
//    """.trimMargin(">")
//
//    println(str2)

}

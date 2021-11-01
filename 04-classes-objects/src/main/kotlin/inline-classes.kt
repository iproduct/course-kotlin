package course.kotlin.inline

// Inline classes
// For JVM backends
@JvmInline
value class Password(private val s: String)

// No actual instantiation of class 'Password' happens
// At runtime 'securePassword' contains just 'String'
val securePassword = Password("Don't try this in production")

@JvmInline
value class Name(val s: String) {
    init {
        require(s.length > 0) { }
    }

    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }
}

fun main1() {
    val name = Name("Kotlin")
    name.greet() // method `greet` is called as a static method
    println(name.length) // property getter is called as a static method
}

// inline classes inheritance
interface Printable {
    fun prettyPrint(): String
}

@JvmInline
value class Name2(val s: String) : Printable {
    override fun prettyPrint(): String = "Let's $s!"
}

fun main2() {
    val name = Name2("Kotlin")
    println(name.prettyPrint()) // Still called as a static method
}


//mangling
@JvmInline
value class UInt(val x: Int)

// Represented as 'public final void compute(int x)' on the JVM
fun compute(x: Int) { }

// Also represented as 'public final void compute(int x)' on the JVM!
fun compute(x: UInt) { }

// calling from java
@JvmInline
value class UInt2(val x: Int)

fun compute2(x: Int) { }

@JvmName("computeUInt")
fun compute2(x: UInt2) { }

// type alias vs inline
typealias NameTypeAlias = String

@JvmInline
value class NameInlineClass(val s: String)

fun acceptString(s: String) {}
fun acceptNameTypeAlias(n: NameTypeAlias) {}
fun acceptNameInlineClass(p: NameInlineClass) {}

fun main() {
    val nameAlias: NameTypeAlias = ""
    val nameInlineClass: NameInlineClass = NameInlineClass("")
    val string: String = ""

    acceptString(nameAlias) // OK: pass alias instead of underlying type
//    acceptString(nameInlineClass) // Not OK: can't pass inline class instead of underlying type

    // And vice versa:
    acceptNameTypeAlias(string) // OK: pass underlying type instead of alias
//    acceptNameInlineClass(string) // Not OK: can't pass underlying type instead of inline class
}




package course.kotlin.inner

import com.sun.javafx.logging.JFRInputEvent
import java.awt.SystemColor.window
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame

class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2

interface OuterInterface {
    class InnerClass
    interface InnerInterface
}

class OuterClass {
    class InnerClass
    interface InnerInterface
}

// Inner classes
class Outer2 {
    private val bar: Int = 1
    inner class Inner2 {
        fun foo() = bar
    }
}

val demo2 = Outer2().Inner2().foo() // == 1

// Qualified this
class A { // implicit label @A
    inner class B { // implicit label @B
        fun Int.foo() { // implicit label @foo
            val a = this@A // A's this
            val b = this@B // B's this

            val c = this // foo()'s receiver, an Int
            val c1 = this@foo // foo()'s receiver, an Int

            val funLit = lambda@ fun String.() {
                val d = this // funLit's receiver
            }

            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
            }
        }
    }
}

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

fun main2() {
    val name = Name("Kotlin")
    name.greet() // method `greet` is called as a static method
    println(name.length) // property getter is called as a static method
}

fun main() {
    // Anonimous inner functions
    val  window = JFrame()
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) { /*...*/ }
        override fun mouseEntered(e: MouseEvent) { /*...*/ }
    })
    val listener = ActionListener { println("clicked") }
}



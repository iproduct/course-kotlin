package course.kotlin.inner

import com.sun.javafx.logging.JFRInputEvent
import foo
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
            inA() // Int, B, A,
            val a = this@A // A's this
            val b = this@B // B's this
            fun String.myfunc():Int  = length
            val c = this // foo()'s receiver, an Int
            val c1 = this@foo // foo()'s receiver, an Int
            val c2 = this@B.compute()
            val c3 = this.minus("abc".myfunc())
            val c4 = this@A.inA()
            val funLit = lambda@ fun String.():Int {
                val d = this.chars() // funLit's receiver == String
                val fooThis = minus(1)
                inA()
                return 42
            }
            val c5 = this.minus(funLit("abc"))


            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
            }
        }
        fun compute() {
            5.foo()
        }
    }
    fun inA(): Int {
//        5.foo()
        return 42
    }
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

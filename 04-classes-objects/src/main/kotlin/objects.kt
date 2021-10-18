package course.kotlin.objects

import java.awt.Dimension
import java.awt.PageAttributes.MediaType.C2
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.WindowConstants
import javax.swing.WindowConstants.EXIT_ON_CLOSE

val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world"
}

// using constructors
open class A1(x: Int) {
    public open val y: Int = x
}

interface B1 { /*...*/ }

val ab: A1 = object : A1(1), B1 {
    override val y = 15
}

// private or local object value
class C1 {
    private fun getObject() = object {
        val x: String = "x"
    }

    fun printX() {
        println(getObject().x)
    }
}

// public or private inline object value
interface A {
    fun funFromA() {}
}
interface B

class C {
    // The return type is Any. x is not accessible
    fun getObject() = object {
        val x: String = "x"
    }

    // The return type is A; x is not accessible
    fun getObjectA() = object: A {
        override fun funFromA() {}
        val x: String = "x"
    }

    // The return type is B; funFromA() and x are not accessible
    fun getObjectB(): B = object: A, B { // explicit return type is required
        override fun funFromA() {}
        val x: String = "x"
    }
}

// accesssing variables
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ...
}

// object declarations
class  DataProvider
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }

    val allDataProviders: Collection<DataProvider>
        get() = // ...
}

fun main() {
    println(helloWorld)

    // Inherit from class
    val window = JFrame("Main Window")
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) { /*...*/ }
        override fun mouseEntered(e: MouseEvent) { /*...*/ }
    })
    window.size = Dimension(600, 400)
    window.defaultCloseOperation = EXIT_ON_CLOSE
//    window.isVisible = true

    // return and value objects
    val c= C1()
    c.printX()

    // object declarations
    DataProviderManager.registerDataProvider(DataProvider())
}

package course.kotlin

class Example // Implicitly inherits from Any

class Context
class AttributeSet
open class View(val ctx: Context) {
    private var attributes: AttributeSet = AttributeSet()

    constructor(ctx: Context, attrs: AttributeSet) : this(ctx) {
        this.attributes = attrs
    }

}

class MyView : View {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}

// Overiding methods
open class Shape {
    open fun draw() { /*...*/
    }

    fun fill() { /*...*/
    }
}

open class Circle() : Shape() {
    override fun draw() { /*...*/
    }
}

class ColoredCircle(): Circle() {
    override fun draw() { /* draw in color...*/
    }
}

open class Rectangle() : Shape() {
    final override fun draw() { /*...*/
    }
}

// Overiding properties
open class Shape2 {
    open val vertexCount: Int = 0
}

class Rectangle2 : Shape2() {
    override val vertexCount = 4
}

interface Shape3 {
    val vertexCount: Int
}

class Rectangle3(count: Int = 4) : Shape3  {
    override val vertexCount: Int = count
}// Always has 4 vertices

class Polygon3 : Shape3 {
    override var vertexCount: Int = 0  // Can be set to any number later
}

// Initialization order
open class Base(val name: String) {
    open val size: Int =
        name.length.also { println("Initializing size in the base class: $it") }

    init {
        println("Initializing a base class: $size")
    }
}
class Derived(
    name: String,
    val lastName: String,
) : Base(name.replaceFirstChar { it.uppercase() }.also { println("Argument for the base class: $it") }) {

    init {
        println("Initializing a derived class")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in the derived class: $it") }
}


// Using super implementation
open class Rectangle4 {
    open fun draw() { println("Drawing a rectangle") }
    val borderColor: String get() = "black"
}

class FilledRectangle : Rectangle4() {
    override fun draw() {
        super.draw()
        println("Filling the rectangle")
    }

    val fillColor: String get() = super.borderColor
}

// in inner class
class FilledRectangle2: Rectangle4() {
    override fun draw() {
        val filler = Filler()
        filler.drawAndFill()
    }

    inner class Filler {
        fun fill() { println("Filling") }
        fun drawAndFill() {
            super@FilledRectangle2.draw() // Calls Rectangle's implementation of draw()
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle2.borderColor}") // Uses Rectangle's implementation of borderColor's get()
        }
    }
}



// Multiple inheritance
open class Rectangle5 {
    open fun draw() { /* ... */ }
}

interface Polygon {
    fun draw() { /* ... */ } // interface members are 'open' by default
}

class Square() : Rectangle5(), Polygon {
    // The compiler requires draw() to be overridden:
    override fun draw() {
        super<Rectangle5>.draw() // call to Rectangle.draw()
        super<Polygon>.draw() // call to Polygon.draw()
    }
}

// abstract classes
abstract class Polygon6 {
    abstract fun draw()
}

class Rectangle6 : Polygon6() {
    override fun draw() {
        // draw the rectangle
    }
}

open class Polygon7{
    open fun draw() {
        // some default polygon drawing method
    }
}

abstract class WildShape : Polygon7() {
    // Classes that inherit WildShape need to provide their own
    // draw method instead of using the default on Polygon
    abstract override fun draw()
}

fun main() {
//    val d = Derived("ivan", "Petrov")
    FilledRectangle2().draw()
}

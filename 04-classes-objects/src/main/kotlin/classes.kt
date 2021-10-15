package course.kotlin

import oracle.jrockit.jfr.openmbean.ProducerDescriptorType
import javax.inject.Inject

class Person { /*...*/ }

class Empty

class Person2 constructor(firstName: String) { /*...*/ }

class Person3(firstName: String) { /*...*/ }

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println) // 1

    init {
        println("First initializer block that prints $name, $firstProperty")  // 2
    }

    val secondProperty = "Second property: ${name.length}".also(::println)  // 3

    init {
        println("Second initializer block that prints ${name.length}")  // 4
    }
}

class Customer(name: String) {
    val customerKey = name.uppercase()
    override fun toString() = "Customer[$customerKey]"
}

class Person4(val firstName: String, val lastName: String, var age: Int)

class Person5 @JvmOverloads constructor(val firstName: String, val lastName: String = "", var isEmployed: Boolean = true) {
    override fun toString(): String = "Person5($firstName $lastName, employed: $isEmployed)"
}

class Person6(
    val firstName: String,
    val lastName: String,
    var age: Int = 25, // trailing comma
) { /*...*/ }

class Customer2 public @Inject constructor(name: String) { /*...*/ }



class Person7(val name: String, val pets: MutableList<Pet> = mutableListOf()) {
    override fun toString() = "$name's pets: $pets"
}


class Pet(val name: String) {
    lateinit var owner: Person7 // subtle bug here - can be uninitialized runtime!
    constructor(name: String, owner: Person7) : this(name) {
        this.owner = owner
        owner.pets.add(this) // adds this pet to the list of its owner's pets
    }

    override fun toString() = "${owner.name}'s Pet: $name"
}

class Constructors {

    constructor(i: Int) {
        println("Constructor $i")
    }

    init {
        println("Init block")
    }

}

// Private constructors and constructors with default values
class DontCreateMe private constructor () { /*...*/ }
class Customer3(val customerName: String = "")

class StringRegistry private constructor(val items: MutableList<String> = mutableListOf()){
    override fun toString() = "StringRegistry(${items})"
    companion object Factory1 {
        val theInstance = StringRegistry()
    }
}

data class Product(val name: String = "", val price: Double =0.0, var id: Int? = null) {
    init {
        println("In Product init ...")
    }
}
data class Invoice(
    val number:Int,
    val customer: Customer,
    val items: MutableList<Product> = mutableListOf()
)

fun main() {
    // Classes and constructors
//    val o1 = InitOrderDemo("Test")
//    val c1 = Customer("ABC Ltd.")
//    println(c1)
//    val p5  = Person5(firstName = "Ivan", isEmployed = true )
//    println(p5)

    // Secondary constructors
//    val ivan = Person7("Ivan Petrov")
//    val johny = Pet("Johny", ivan)
//    val silvester = Pet("Silvester", ivan)
//    val caty = Pet("Caty")
//    println(ivan) //Ivan Petrov's pets: [Pet(Johny), Pet(Silvester), Pet(Caty)]
//    println(caty)
//    val micky = Pet("Micky Mouse")
//    println(micky)

    // Constructor delegation
//    val c2 = Constructors(42) // Init block, Constructor 42

    // Private constructor demo - singleton
//    val reg = StringRegistry.Factory1.theInstance
//    println(reg)

    // Instances
    val customer = Customer("Joe Smith")
    val invoice = Invoice(1, customer, mutableListOf(Product("Laptop", 1200.0,  1), Product()))
    println(invoice) // Invoice(number=1, customer=Customer: JOE SMITH, items=[])

}

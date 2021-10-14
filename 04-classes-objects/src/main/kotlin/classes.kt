package course.kotlin

import oracle.jrockit.jfr.openmbean.ProducerDescriptorType
import javax.inject.Inject

class Person { /*...*/ }

class Empty

class Person2 constructor(firstName: String) { /*...*/ }

class Person3(firstName: String) { /*...*/ }

class InitOrderDemo(name: String) {
    init {
        println("First initializer block that prints ${name}")  // 2
    }

    val firstProperty = "First property: $name".also(::println) // 1

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

class Person5(val firstName: String, val lastName: String, var isEmployed: Boolean = true) {
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
    constructor(name: String, owner: Person7) : this(name) {
        owner.pets.add(this) // adds this pet to the list of its owner's pets
    }

    override fun toString() = "Pet($name)"
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
//    val p5  = Person5("Ivan", "Petrov")
//    println(p5)
//
//    // Secondary constructors
//    val ivan = Person7("Ivan Petrov")
//    val Johny = Pet("Johny", ivan)
//    val Silvester = Pet("Silvester", ivan)
//    val Caty = Pet("Caty", ivan)
//    println(ivan) //Ivan Petrov's pets: [Pet(Johny), Pet(Silvester), Pet(Caty)]
//    val micky = Pet("Micky Mouse")
//    println(micky)

    // Constructor delegation
//    val c2 = Constructors(42) // Init block, Constructor 42
//
    // Instances
    val customer = Customer("Joe Smith")
    val invoice = Invoice(1, customer, mutableListOf(Product("Laptop", 1200.0,  1), Product()))
    println(invoice) // Invoice(number=1, customer=Customer: JOE SMITH, items=[])

}

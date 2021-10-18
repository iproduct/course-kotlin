package course.kotlin.interfaces

import course.kotlin.Person3

interface MyInterface {
    fun bar()
    fun foo() {
        // optional body
    }
}

class Child : MyInterface {
    override fun bar() {
        // body
    }
}

interface MyInterface2 {
    val prop: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}

class Child2 : MyInterface2 {
    override val prop: Int = 29
}

// Interface inheritance
interface Named {
    val name: String
}

interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName $lastName"
}

data class Position(val name: String)

data class Employee (
    // implementing 'name' is not required
    override val firstName: String,
    override val lastName: String,
    val position: Position
) : Person

// conflict resolution
interface A {
    fun foo() { print("A") }
    fun bar()
}

interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

class C : A {
    override fun bar() { print("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}


fun main() {
    val e = Employee("Ivan", "Petrov", Position("Manager"))
    println("${e.name}")
}

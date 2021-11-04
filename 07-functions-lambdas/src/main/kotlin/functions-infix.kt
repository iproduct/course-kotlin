package course.kotlin.functions

fun main() {
    infix fun Int.shl(x: Int): Int { /*...*/ return 42}

// calling the function using the infix notation
    1 shl 2

// is the same as
    1.shl(2)

}

class MyStringCollection {
    infix fun add(s: String) { /*...*/ }

    fun build() {
        this add "abc"   // Correct
        add("abc")       // Correct
        //add "abc"        // Incorrect: the receiver must be specified
    }
}

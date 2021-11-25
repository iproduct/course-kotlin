package collections

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.associateWith { it.length })

    val numbers2 = listOf("one", "two", "three", "four")
    println(numbers2.associateBy { it.first().uppercaseChar() })
    println(numbers2.associateBy(keySelector = { it.first().uppercaseChar() }, valueTransform = { it.length }))

    val names3 = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names3.associate { name -> parseFullName(name).let { it.lastName to it.firstName } })

}

interface Name {
    val firstName: String;
    val lastName: String
}

fun parseFullName(name: String): Name {
    val names = name.split(" ")
    return object : Name {
        override val firstName = names[0]
        override val lastName = names[1]
    }
}

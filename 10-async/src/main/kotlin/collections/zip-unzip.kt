package collections

fun main() {
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")

    val animalPairs = colors.zip(animals)

    println(animalPairs
        .map{ (color, animal) -> "The ${animal.replaceFirstChar { it.uppercase() }} is $color"}
        .toList())
    println(animalPairs.unzip())
}

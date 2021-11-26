package sequence

fun main() {
    val numbersSequence = sequenceOf("four", "three", "two", "one")

    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence2 = numbers.asSequence()

    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    println(oddNumbers.take(5).toList())
    println(oddNumbers.take(5).toList())
    //println(oddNumbers.count())     // error: the sequence is infinite

    val oddNumbersLessThan10 = generateSequence(1) { if (it < 8) it + 2 else null }
    println(oddNumbersLessThan10.toList())
    println(oddNumbersLessThan10.count())

    // from chunks
    val oddNumbers2 = sequence {
        for (i in 1..10) {
            yield(i)
        }
        yieldAll(listOf(3, 5).asSequence())
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers2.take(15).toList())
}

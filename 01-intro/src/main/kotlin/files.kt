package course.kotlin

import java.io.File
import java.io.BufferedReader
import java.io.InputStream

fun main(args: Array<String>) {
    // Using BufferedReader
    val bufferedReader: BufferedReader = File("example.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    println(inputString)

    // Read by line
    val inputStream2: InputStream = File("example.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream2.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach{println(">  " + it)}

    // Read all lines
    val inputStream3: InputStream = File("example.txt").inputStream()
    val inputString3 = inputStream3.bufferedReader().use { it.readText() }
    println(inputString)

    // Read file directly
    val lineList4 = mutableListOf<String>()
    File("example.txt").useLines { lines -> lines.forEach { lineList4.add(it) }}
    lineList4.forEach { println(">  " + it) }
}

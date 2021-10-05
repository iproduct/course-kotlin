package course.kotlin

import java.io.Reader
import java.nio.charset.Charset

fun main(args: Array<String>) {
    println("Hello World!: ${args.contentToString()}")
    println("What's your name?")
    val name= readLine()
    println("Hello $name!")
}

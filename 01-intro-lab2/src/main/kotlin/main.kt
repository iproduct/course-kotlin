package course.kotlin

fun main(args: Array<String>) {
    println("Hello World!: ${args.contentToString()}")
//    println("What's your name?")
//    val name = readLine()
//    println("Hello, $name!")
//    val sum = {a: Int, b:Int ->  a + b} // lambda
//    val sum = fun(a: Int, b:Int) = a + b // anonymous function
    fun sum(a: Int = 0, b: Int = 0) = a + b
    val x = 1
    val y = 2
    println(sum(b= 5))


}

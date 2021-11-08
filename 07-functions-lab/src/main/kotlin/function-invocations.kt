val repeatFun: String.(Int) -> String = { times ->
    this.repeat(times)
}
val twoParameters: (String, Int) -> String = repeatFun

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}
fun runTransformation2(f: String.(Int) -> String): String {
    return f("hello", 5)
}
fun main() {
    println(runTransformation(twoParameters))
    println(runTransformation(repeatFun))
    println(runTransformation2(twoParameters))
    println(runTransformation2(repeatFun))

}

package course.kotlin

fun main(args: Array<String>) {
    //read input
    val n = readLine()!!.toInt()
    val a = readLine()!!
    val fmap = readLine()!!.split(" ").map { it.toInt() }

    //greedy maximum search
    fun f(c: Char) = '0' + fmap[c - '1']
    val start = a.indexOfFirst { f(it) > it }
        .takeIf { it >= 0 } ?: a.length
    val end = a.withIndex().indexOfFirst { (i, c) -> i > start && f(c) < c }
        .takeIf { it >= 0 } ?: a.length

    val result = a.slice(0 until start) +
            a.slice(start until end).map { f(it) }.joinToString("") +
            a.slice(end until a.length)
    println(result)
}

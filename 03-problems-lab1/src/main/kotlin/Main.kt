fun main(args: Array<String>) {
    // read input
    val n = readLine()!!.toInt()
    val a = readLine()!!
    val fm = readLine()!!.split(" ").map { it.toInt() }

    // read input from file
    // Using BufferedReader


    // define f func
    fun f(c: Char) = '0' + fm[c - '1']

    // greedy maximum search
    val s = a.indexOfFirst { f(it) > it }
        .takeIf { it >= 0 } ?: a.length
    val e = a.withIndex().indexOfFirst { (i, c) -> i > s && f(c) < c }
        .takeIf { it >= 0 } ?: a.length
    val result = a.slice(0 until s) +
            a.slice(s until e).map { f(it) }.joinToString("")+
            a.slice(e until a.length)
    println(result)
}

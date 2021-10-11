import java.io.File

data class LongNumberProblem(
    var n: Int,
    var a: String,
    var fm: List<Int>,
)

fun main(args: Array<String>) {
    // read input
//    val n = readLine()!!.toInt()
//    val a = readLine()!!
//    val fm = readLine()!!.split(" ").map { it.toInt() }

    // read input from file
    // Using BufferedReader
    val bf = File("./long-number01.txt").bufferedReader()
    val problems = mutableListOf<LongNumberProblem>()
    var i = 0
    var n: Int = 0
    var a: String = ""
    var fm: List<Int> = emptyList()
    bf.useLines { lines -> lines.forEach {
            when (i++ % 3) {
                0 -> n = it.toInt()
                1 -> a = it
                2 -> {
                    fm = it.split(" ").map { c -> c.toInt() }
                    problems.add(LongNumberProblem(n, a, fm))
                }
            }
        }
    }

    for (p in problems) {
        println(solveLongNumber(p))
    }
}

private fun solveLongNumber(problem: LongNumberProblem): String {
    fun f(c: Char) = '0' + problem.fm[c - '1']

    // greedy maximum search
    val s = problem.a.indexOfFirst { f(it) > it }
        .takeIf { it >= 0 } ?: problem.a.length
    val e = problem.a.withIndex().indexOfFirst { (i, c) -> i > s && f(c) < c }
        .takeIf { it >= 0 } ?: problem.a.length
    val result = problem.a.slice(0 until s) +
            problem.a.slice(s until e).map { f(it) }.joinToString("") +
            problem.a.slice(e until problem.a.length)
    return result
}

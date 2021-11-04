package course.kotlin.functions

class Sample {
    fun foo() { print("Foo") }
}

fun main() {
    Sample().foo() // creates instance of class Sample and calls foo

}

// local function, closure
data class Graph(var vertices: List<Vertex>, )
data class Vertex(var neighbors: List<Vertex> = emptyList())

fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}

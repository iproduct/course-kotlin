package course.kotlin.functions

class HTML{
    fun head() { /*...*/ }
    fun body() { /*...*/ }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // create the receiver object
    html.init()        // pass the receiver object to the lambda
    return html
}

fun main() {
    html{
        head()   // lambda with receiver begins here
        body()   // calling a method on the receiver object
    }
}


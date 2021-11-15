import course.kotlin.functions.foo
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeNode


inline fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
       lock.unlock()
    }
}
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) { /*...*/ }

// return
fun ordinaryFunction(f: ()->Unit){}
fun foo() {
    ordinaryFunction {
//        return // ERROR: cannot make `foo` return here
    }
}

inline fun inlined(f: ()->Unit){}
fun fooWithInlined() {
    inlined {
        return // OK: the lambda is inlined
    }
}

fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        println(it)
        if (it == 0) return true // returns from hasZeros
    }
    return false
}

inline fun f(crossinline body: () -> Unit) {
    val f = object: Runnable {
        override fun run() = body()
    }
    // ...
}

fun main() {
    val foo = fun(){}
    val l = ReentrantLock()
    lock(l) { foo() }
//    l.lock()
//    try {
//        foo()
//    } finally {
//        l.unlock()
//    }
}

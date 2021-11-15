import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeNode

// reified type params
fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
        val ctor = T::class.java.constructors.iterator().next()
        val nt = ctor.newInstance()
    }
    return p as T?
}

inline fun <reified T> membersOf() = T::class.members
fun main(s: Array<String>) {
    // reified type params
//    class MyTreeNode: DefaultMutableTreeNode()
//    val treeNode = DefaultMutableTreeNode("The Java Series");
//    treeNode.findParentOfType(MyTreeNode::class.java)

    // using reflection
    println(membersOf<StringBuilder>().joinToString("\n"))
}

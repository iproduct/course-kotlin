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
    }
    return p as T?
}

inline fun <reified T> membersOf() = T::class.members
fun main2(s: Array<String>) {
    // reified type params
    class MyTreeNode: DefaultMutableTreeNode()
    val treeNode = DefaultMutableTreeNode("The Java Series");
    treeNode.findParentOfType(MyTreeNode::class.java)

    // using reflection
    println(membersOf<StringBuilder>().joinToString("\n"))
}

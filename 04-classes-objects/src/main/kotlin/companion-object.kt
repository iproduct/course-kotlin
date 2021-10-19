package course.kotlin

class MyClass {
    companion object {
        lateinit var theInstance: MyClass
        init {
            theInstance = create()
        }
        private fun create(): MyClass = MyClass()
        fun getInstance() = theInstance

    }
}

val instance = MyClass.Companion.getInstance()

class MyClass2 {
    companion object {
        object Factory {

        }
    }
}

val x = MyClass2.Companion


class MyClass3 {
    companion object Named {}
}

val x3 = MyClass3

class MyClass4 {
    companion object {}
}

val y = MyClass4

interface Factory<T> {
    fun create(ctx: Context, name: String): T
}

data class Context2(val name: String)

data class MyClass5 private constructor(val ctx: Context) {
    var name: String = "unnamed"
    override fun toString(): String = "MyClass5(ctx=$ctx, name=$name)"

    object MyFactory : Factory<MyClass5> {
        override fun create(ctx: Context, aName: String): MyClass5 =
            MyClass5(ctx).apply { this.name = aName  }
    }
}

fun main() {
//    MyClass5.MyFactory.create(Context())
    val mc5 = MyClass5.MyFactory.create(Context(), "MyClass5Instance")
    println(mc5)
}

package course.kotlin

class MyClass {
    companion object {
        lateinit var theInstance: MyClass
        init {
            val theInstance = create()
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
    companion object Named { }
}

val x3 = MyClass3

class MyClass4 {
    companion object { }
}

val y = MyClass4

interface Factory<T> {
    fun create(): T
}

class MyClass5 {
    companion object : Factory<MyClass5> {
        override fun create(): MyClass5 = MyClass5()
    }
}

val f: Factory<MyClass5> = MyClass5

package course.kotlin

class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

val instance = MyClass.create()


class MyClass2 {
    companion object { }
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

package course.kotlin

class Address(val init: () -> String) {
    var city = init()
    override fun toString() = city.toString()
    fun setCity(provider: () -> String) {
        city = provider.invoke()
    }
}

class User(var name: String, val init: () -> Address) {
    var addr = init()
    override fun toString() = "$name: ${addr.city}"
//    fun address(provider: () -> Address): Address {
//        addr = provider.invoke()
//        return addr
//    }
}

fun User.address(modifier: Address.() -> Unit) {
    modifier.invoke(addr)
}

fun main() {
    val u = User("Ivan Petrov") {
        Address { "Sofia 1000" }
    }


    println(u)

    u.address {
        city = "Plovdiv 2000"
    }

    println(u)
}


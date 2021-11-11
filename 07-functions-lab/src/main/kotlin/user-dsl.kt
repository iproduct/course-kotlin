@DslMarker
annotation class UserDslMarker

@UserDslMarker
data class Address (var city: String? = null, var street: String? = null) {}

@UserDslMarker
data class Role (var name: String? = null, var permisiions: List<Permission> = emptyList()) {}

@UserDslMarker
data class User(var name: String? = null, var address: Address = Address(), var role: Role = Role()) {
    fun role(init: Role.()->Unit): Role {
        role.init()
        return role
    }
    fun address(init: Address.()->Unit): Address {
        address.init()
        return address
    }
}

fun user(init: User.()->Unit): User {
    val u = User()
    u.init()
    return u
}


fun main() {
    val trayan = user {
        name = "Trayan Iliev"
        address {
            city = "Sofia"
            street = "Tzar Osvoboditel 50"
        }
        role {
            name = "Admin"
            permisiions = listOf(Permission.READ, Permission.WRITE)
        }
    }
    println(trayan)
}

enum class Permission{
    READ, WRITE, DELETE
}

package course.kotlin.users
@DslMarker
annotation class UserDslMarker

@UserDslMarker
data class User(var name: String? = null, var address: Address = Address(), var role: Role = Role()) {
    fun address(init: Address.() -> Unit): Address {
        address.init()
        return address
    }
    fun role(init: Role.() -> Unit): Role {
        role.init()
        return role
    }
}
@UserDslMarker
data class Address(var country: String? = null, var city: String? = null, var street: String? = null)
@UserDslMarker
data class Role(var name: RoleName = RoleName.READER, var permissions: List<String> = emptyList())

fun user(init: User.() -> Unit): User {
    var u = User()
    u.init()
    return u
}

enum class RoleName {
    READER, AUTHOR, ADMIN
}

fun main() {
    val u1 =
        user {
            name = "Trayan Iliev"
            address {
                country = "BG"
                city = "Sofia"
                street = "Tzar Osvoboditel 50"
            }
            role {
                name = RoleName.ADMIN
                permissions = listOf("read", "write", "execute")
            }
        }
    println(u1)
}

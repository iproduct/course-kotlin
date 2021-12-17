package course.kotlin.spring.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Document(collection = "users")
data class User(
    @field:NotNull @field:Size(min = 2, max = 40) val firstName: String,
    @field:NotNull @field:Size(min = 2, max = 40) val lastName: String,
    @field:NotNull @field:Size(min = 2, max = 40) internal val username: String,
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    internal val password: String? = null,
    val role: Role = Role.READER,
    val pictureUrl: String? = null,
    val active: Boolean = true,
    @ReadOnlyProperty
    @DocumentReference(lookup="{'author':?#{#self._id} }")
    val blogs: MutableList<Blog> = mutableListOf<Blog>(),
    val created: LocalDateTime = LocalDateTime.now(),
    val modified: LocalDateTime = LocalDateTime.now(),
    @Id val id: String? = null,
) : UserDetails {
    val name: String
        get() = "$firstName $lastName"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_" + role.toString()))

    override fun getPassword(): String? = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = active

    override fun isAccountNonLocked(): Boolean = active

    override fun isCredentialsNonExpired(): Boolean = active

    override fun isEnabled(): Boolean = active
}

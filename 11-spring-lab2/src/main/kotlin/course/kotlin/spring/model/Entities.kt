package course.kotlin.spring.model

import course.kotlin.spring.extensions.toSlug
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
class Blog(
    @NotNull @Size(min = 2, max = 60) var title: String,
    @NotNull @Size(min = 10, max = 2048) var content: String,
    @ManyToOne var author: User,
    var pictureUrl: String? = null,
    var slug: String = title.toSlug(),
    var created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null,
)

@Entity
class User(
    @NotNull @Size(min = 2, max = 40) var firstName: String,
    @NotNull @Size(min = 2, max = 40) var lastName: String,
    @NotNull @Size(min = 2, max = 40) var username: String,
    @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    var password: String,

    var role: Role = Role.READER,
    var pictureUrl: String? = null,
    var active: Boolean = true,
    @OneToMany val blogs: MutableList<Blog> = mutableListOf<Blog>(),
    var created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null,
) : UserDetails {
    val name: String
        get() = "$firstName $lastName"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_" + role.toString()))

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}

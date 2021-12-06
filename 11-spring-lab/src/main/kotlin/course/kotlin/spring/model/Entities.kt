package course.kotlin.spring.model

import com.fasterxml.jackson.annotation.JsonProperty
import course.kotlin.spring.extensions.toSlug
import org.springframework.data.annotation.AccessType
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.print.DocFlavor
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
class Blog(
    @NotNull @Size(min = 2, max = 60) var title: String,
    @NotNull @Size(min = 10, max = 2048) var content: String,
    @ManyToOne var author: User,
    var slug: String = title.toSlug(),
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null,
)

@Entity
class User (
    @NotNull @Size(min = 2, max = 40) var firstName: String,
    @NotNull @Size(min = 2, max = 40) var lastName: String,
    @NotNull @Size(min = 2, max = 30) internal var username: String,
    @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    internal var password: String,
    @NotNull var role: Role  = Role.READER,
    var active: Boolean = true,
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${role.toString()}"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return active
    }

    override fun isAccountNonLocked(): Boolean {
        return active
    }

    override fun isCredentialsNonExpired(): Boolean {
        return active
    }

    override fun isEnabled(): Boolean {
        return active
    }
}

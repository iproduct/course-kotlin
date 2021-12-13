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
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
class Blog(
    @field:NotEmpty @field:Size(min = 2, max = 60, message = "{blog.title.size}") var title: String,
    @field:NotEmpty @field:Size(min = 10, max = 2048, message = "{blog.content.size}") var content: String,
    @ManyToOne var author: User? = null,
    var slug: String = title.toSlug(),
    var pictureUrl: String? = null,
    @Id @GeneratedValue var id: Long? = null,
) {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var created: LocalDateTime = LocalDateTime.now()
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var modified: LocalDateTime = LocalDateTime.now()
}

@Entity
class User(
    @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") var firstName: String,
    @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") var lastName: String,
    internal @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") var username: String,
    internal @field:NotEmpty @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "{password.message}"
    )
    var password: String,
    @field:NotNull var role: Role = Role.READER,
    var active: Boolean = true,
    var pictureUrl: String? = null,
    var created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now(),
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

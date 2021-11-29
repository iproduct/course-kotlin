package course.kotlin.spring.entities

import com.fasterxml.jackson.annotation.JsonProperty
import course.kotlin.spring.extensions.toSlug
import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
class Blog(
    @Id @GeneratedValue var id: Long? = null,
    @NotNull @Size(min = 2, max = 60) var title: String,
    @Size(min = 10, max = 2048) var content: String,
    @ManyToOne var author: User,
    var slug: String = title.toSlug(),
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
)

@Entity
class User(
    @Id @GeneratedValue var id: Long? = null,
    @NotNull @Length(min = 2, max = 16) var firstName: String,
    @NotNull @Length(min = 2, max = 16) var lastName: String,
    @NotNull @Length(min = 3, max = 16) var username: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    var password:  String? = null,
    var roles: Role = Role.READER,
    var active:Boolean = true,
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
) {
    val name: String
        get() = "$firstName $lastName"
}


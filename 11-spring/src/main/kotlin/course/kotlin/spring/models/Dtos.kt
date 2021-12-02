package course.kotlin.spring.entities

import com.fasterxml.jackson.annotation.JsonProperty
import course.kotlin.spring.extensions.toSlug
import course.kotlin.spring.models.Role
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
import kotlin.reflect.full.memberProperties

class BlogCreateView(
    var id: Long? = null,
    @NotNull @Size(min = 2, max = 60) var title: String,
    @Size(min = 10, max = 2048) var content: String,
    var slug: String = title.toSlug(),
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
)

fun BlogCreateView.toBlogReflection() = with(::Blog) {
    val propertiesByName = BlogCreateView::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            else -> propertiesByName[parameter.name]?.get(this@toBlogReflection)
        }
    })
}

class BlogDetailsView(
    var id: Long? = null,
    @NotNull @Size(min = 2, max = 60) var title: String,
    @Size(min = 10, max = 2048) var content: String,
    @ManyToOne var author: User,
    var slug: String = title.toSlug(),
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
)

fun Blog.toBlogDetailsViewReflection() = with(::BlogDetailsView) {
    val propertiesByName = Blog::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            else -> propertiesByName[parameter.name]?.get(this@toBlogDetailsViewReflection)
        }
    })
}

class UserCreateView(
    @NotNull @Length(min = 2, max = 16) var firstName: String,
    @NotNull @Length(min = 2, max = 16) var lastName: String,
    @NotNull @Length(min = 3, max = 16) var username: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    var password: String? = null,
    var roles: Role = Role.READER,
    var active: Boolean = true,
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
) {
    val name: String
        get() = "$firstName $lastName"
}

fun UserCreateView.toUser() = UserCreateView(
    firstName = firstName,
    lastName = lastName,
    username = username,
    password = password,
    roles = roles,
    active = active,
    pictureUrl = pictureUrl,
    created = created,
    modified = modified,
)

fun UserCreateView.toUserReflection() = with(::User) {
    val propertiesByName = UserCreateView::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to propertiesByName[parameter.name]?.get(this@toUserReflection)
    })
}

class UserDetailsView(
    @NotNull @Length(min = 2, max = 16) var firstName: String,
    @NotNull @Length(min = 2, max = 16) var lastName: String,
    @NotNull @Length(min = 3, max = 16) var username: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    var password: String? = null,
    var roles: Role = Role.READER,
    var active: Boolean = true,
    var pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var modified: LocalDateTime = LocalDateTime.now(),
) {
    val name: String
        get() = "$firstName $lastName"
}

fun User.toUserDetailsView() = UserDetailsView(
    firstName = firstName,
    lastName = lastName,
    username = username,
    password = password,
    roles = roles,
    active = active,
    pictureUrl = pictureUrl,
    created = created,
    modified = modified,
)

fun User.toUserDetailsViewReflection() = with(::UserCreateView) {
    val propertiesByName = User::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            UserCreateView::name.name -> "$firstName $lastName"
            else -> propertiesByName[parameter.name]?.get(this@toUserDetailsViewReflection)
        }
    })
}


package course.kotlin.spring.model

import course.kotlin.spring.extensions.toSlug
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

//Blog Dtos
class BlogCreateView(
    val id: Long? = null,
    @NotNull @Size(min = 2, max = 60) val title: String,
    @NotNull @Size(min = 10, max = 2048) val content: String,
    val slug: String = title.toSlug(),
    val pictiureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now()
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
    val id: Long? = null,
    @NotNull @Size(min = 2, max = 60) val title: String,
    @NotNull @Size(min = 10, max = 2048) val content: String,
    @ManyToOne val author: User,
    val slug: String = title.toSlug(),
    val pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now()
)

fun Blog.toBlogDetailsViewReflection() = with(::BlogDetailsView) {
    val propertiesByName = Blog::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            else -> propertiesByName[parameter.name]?.get(this@toBlogDetailsViewReflection)
        }
    })
}


// User Dtos
class UserCreateView(
    val id: Long? = null,
    @NotNull @Size(min = 2, max = 40) val firstName: String,
    @NotNull @Size(min = 2, max = 40) val lastName: String,
    @NotNull @Size(min = 2, max = 30) val username: String,
    @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    val password: String,
    @NotNull val role: Role = Role.READER,
    val active: Boolean = true,
    val pictiureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now()
)

class UserDetailsView(
    val id: Long? = null,
    @NotNull @Size(min = 2, max = 40) val firstName: String,
    @NotNull @Size(min = 2, max = 40) val lastName: String,
    @NotNull @Size(min = 2, max = 30) val username: String,
    @NotNull val role: Role = Role.READER,
    val active: Boolean = true,
    val pictiureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now(),
    val name: String,
) {
//    val name: String
//        get() = "$firstName $lastName"
}

fun UserCreateView.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    password = password,
    role = role,
    active = active,
    pictiureUrl = pictiureUrl
)

fun User.toUserDetailsViewReflection() = with(::UserCreateView) {
    val propertiesByName = User::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            UserDetailsView::name.name -> "$firstName $lastName"
            else -> propertiesByName[parameter.name]?.get(this@toUserDetailsViewReflection)
        }
    })
}




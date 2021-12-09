package course.kotlin.spring.model

import course.kotlin.spring.extensions.format
import course.kotlin.spring.extensions.toSlug
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.ManyToOne
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import kotlin.reflect.full.memberProperties

//Blog Dtos
class BlogCreateView(
    @field:NotEmpty @field:Size(min = 2, max = 60, message = "{blog.title.size}") val title: String,
    @field:NotEmpty @field:Size(min = 10, max = 2048, message = "{blog.content.size}") val content: String,
    val pictureUrl: String? = null,
    val slug: String = title.toSlug(),
    val id: Long? = null,
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
    val id: Long,
    val title: String,
    val content: String,
    val author: User,
    val slug: String = title.toSlug(),
    val pictureUrl: String? = null,
    val created: String,
    val modified: String
)

fun Blog.toBlogDetailsView() = with(::BlogDetailsView) {
    val propertiesByName = Blog::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            BlogDetailsView::created.name -> created.format()
            BlogDetailsView::modified.name -> created.format()
            else -> propertiesByName[parameter.name]?.get(this@toBlogDetailsView)
        }
    })
}


// User Dtos
class UserCreateView(
    val id: Long? = null,
    @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") val firstName: String,
    @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") val lastName: String,
    @field:NotEmpty @field:Size(min = 2, max = 30, message = "{user.name.size}") val username: String,
    @field:NotEmpty @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "{password.message}")
    val password: String,
    @NotNull val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
)

class UserDetailsView(
    val id: Long? = null,
    val name: String,
    val username: String,
    val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now(),
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
    pictureUrl = pictureUrl
)

fun User.toUserDetailsView() = with(::UserCreateView) {
    val propertiesByName = User::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            UserDetailsView::name.name -> "$firstName $lastName"
            else -> propertiesByName[parameter.name]?.get(this@toUserDetailsView)
        }
    })
}




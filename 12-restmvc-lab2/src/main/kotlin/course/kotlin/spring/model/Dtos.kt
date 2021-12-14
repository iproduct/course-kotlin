package course.kotlin.spring.model

import course.kotlin.spring.extensions.format
import course.kotlin.spring.extensions.toSlug
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.*
import kotlin.reflect.full.memberProperties

//Blog Dtos
class BlogCreateView(
    @field:NotBlank @field:Size(min = 2, max = 60, message = "{validation.title.size}") val title: String,
    @field:NotBlank @field:Size(min = 10, max = 2048, message = "{validation.content.size}") val content: String,
    val slug: String = title.toSlug(),
    val pictureUrl: String? = null,
    val id: Long? = null,
)

fun BlogCreateView.toBlog() = Blog(
    title = title,
    content = content,
    pictureUrl = pictureUrl,
    slug = slug,
    id = id
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
    @NotNull @Size(min = 2, max = 60) val title: String,
    @NotNull @Size(min = 10, max = 2048) val content: String,
    @ManyToOne val author: UserDetailsView? = null,
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
            BlogDetailsView::modified.name -> modified.format()
            BlogDetailsView::author.name -> author?.toUserDetailsView()
            else -> propertiesByName[parameter.name]?.get(this@toBlogDetailsView)
        }
    })
}

// User Dtos
class UserCreateView(
    @NotNull @Size(min = 2, max = 40) val firstName: String,
    @NotNull @Size(min = 2, max = 40) val lastName: String,
    @NotNull @Size(min = 2, max = 30) val username: String,
    @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    val password: String,
    @NotNull val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val created: LocalDateTime = LocalDateTime.now(),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val modified: LocalDateTime = LocalDateTime.now(),
    val id: Long? = null,
)

class UserDetailsView(
    val id: Long? = null,
    val username: String,
    val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
    val created: LocalDateTime = LocalDateTime.now(),
    val modified: LocalDateTime = LocalDateTime.now(),
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
    pictureUrl = pictureUrl
)

fun User.toUserDetailsView() = with(::UserDetailsView) {
    val propertiesByName = User::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            UserDetailsView::name.name -> "$firstName $lastName"
            else -> propertiesByName[parameter.name]?.get(this@toUserDetailsView)
        }
    })
}




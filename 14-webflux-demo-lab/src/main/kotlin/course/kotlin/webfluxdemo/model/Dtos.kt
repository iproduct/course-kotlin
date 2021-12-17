package course.kotlin.spring.model

import course.kotlin.spring.extensions.format
import course.kotlin.spring.extensions.toSlug
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import kotlin.reflect.full.memberProperties

//Blog Dtos
data class BlogCreateView(
    @field:NotBlank @field:Size(min = 2, max = 60, message = "{validation.title.size}") val title: String,
    @field:NotBlank @field:Size(min = 10, max = 2048, message = "{validation.content.size}") val content: String,
    val slug: String = title.toSlug(),
    val pictureUrl: String? = null,
    val id: String? = null,
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

data class BlogDetailsView(
    val id: String,
    val title: String,
    val content: String,
    val author: UserDetailsView? = null,
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
data class UserCreateView(
    @field:NotNull @field:Size(min = 2, max = 40) val firstName: String,
    @field:NotNull @field:Size(min = 2, max = 40) val lastName: String,
    @field:NotNull @field:Size(min = 2, max = 30) val username: String,
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    val password: String? = null,
    @field:NotNull val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
    val id: String? = null,
)

data class UserDetailsView(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val username: String,
    val role: Role = Role.READER,
    val active: Boolean = true,
    val pictureUrl: String? = null,
    val created: String,
    val modified: String,
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
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            UserDetailsView::created.name -> created.format()
            UserDetailsView::modified.name -> modified.format()
            else -> propertiesByName[parameter.name]?.get(this@toUserDetailsView)
        }
    })
}

data class UserProjectExperience(
    val userId: String,
    val experience: String,
    val competencies: String,
)

data class UserHRData(
    val userId: String,
    val salary: String,
    val numSubordinates: Int,
)

data class UserWithDetails (
    val user: User,
    val projectExperience: UserProjectExperience,
    val hrData: UserHRData,
)

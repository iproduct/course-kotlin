package course.kotlin.spring.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
data class Blog(
    @Id @GeneratedValue var id: Long? = null,
    @NotNull @Size(min = 2, max = 60) var title: String,
    @NotNull @Size(min = 10, max = 2048) var content: String,
    @ManyToOne var author: User,
    var pictureUrl: String? = null,
    var created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now(),
)

@Entity
data class User(
    @Id @GeneratedValue var id: Long? = null,
    @NotNull @Size(min = 2, max = 40) var firstName: String,
    @NotNull @Size(min = 2, max = 40) var lastName: String,
    @NotNull @Size(min = 2, max = 40) var username: String,
    @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
    var password: String,
    var role: Role = Role.READER,
    var pictureUrl: String? = null,
    @OneToMany val blogs: MutableList<Blog> = mutableListOf<Blog>(),
    var created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now(),
) {
    val name: String
        get() = "$firstName $lastName"
}

package course.kotlin.spring.model

import course.kotlin.spring.extensions.toSlug
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Document(collection = "blogs")
data class Blog(
    @field:NotNull @field:Size(min = 2, max = 60) val title: String,
    @field:NotNull @field:Size(min = 10, max = 2048) val content: String,
    @DocumentReference(lazy=true)
    val author: User? = null,
    val pictureUrl: String? = null,
    val slug: String = title.toSlug(),
    val created: LocalDateTime = LocalDateTime.now(),
    val modified: LocalDateTime = LocalDateTime.now(),
    @Id var id: String? = null,
)

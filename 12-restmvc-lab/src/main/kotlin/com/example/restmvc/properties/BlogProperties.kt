package course.kotlin.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("blog", ignoreInvalidFields=true)
@ConstructorBinding
data class BlogProperties(val api: Upload) {
    data class Upload(val prefix: String = "uploads")
}

package course.kotlin.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("blog", ignoreInvalidFields=true)
@ConstructorBinding
data class BlogProperties(var title:String? = null, val upload: Upload) {
    data class Upload(val dir: String = "file:uploads")
}

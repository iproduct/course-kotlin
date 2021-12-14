package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.BlogDetailsView
import course.kotlin.spring.model.UserDetailsView
import course.kotlin.spring.model.toBlogDetailsView
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/blogs")
class BlogsRestController(
    private val blogsService: BlogsService
) {
    @GetMapping
    @RequestMapping("/hello")
    fun sayHello(): String = "Hello from Kotlin REST service"

    @GetMapping
    @RequestMapping
    fun getBlogs(): List<BlogDetailsView> {
//        log().info(blogsService.findAll().toString())
        return blogsService.findAll().map {
            it.toBlogDetailsView()
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    fun getBlogById(@PathVariable id: Long): BlogDetailsView {
//        log().info(blogsService.findById(id).toString())
        return blogsService.findById(id).toBlogDetailsView()
    }
}

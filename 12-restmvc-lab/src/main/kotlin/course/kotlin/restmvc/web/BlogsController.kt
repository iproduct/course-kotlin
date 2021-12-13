package course.kotlin.restmvc.web

import course.kotlin.restmvc.dao.BlogsRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/blogs")
class BlogsRestController(
    private val blogsRepository: BlogsRepository,
) {
    @GetMapping
    @RequestMapping("/hello")
    fun sayHello(): String = "Hello from Kotlin REST service"

    @GetMapping
    @RequestMapping
    fun getBlogs() = blogsRepository.findAllByOrderByCreatedDesc()

    @GetMapping
    @RequestMapping("/{id}")
    fun getBlogById(@PathVariable id: Long) =
        blogsRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Blog with ID=$id not found.")
        }
}

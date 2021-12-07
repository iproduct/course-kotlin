package course.kotlin.spring.web

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class HelloController(
    private val blogsRepository: BlogsRepository
) {
    @GetMapping
    @RequestMapping("/hello")
    fun sayHello(): String = "Hello from Kotlin REST service"

    @GetMapping
    @RequestMapping("/blogs")
    fun getBlogs() = blogsRepository.findAll()

    @GetMapping
    @RequestMapping("/blogs/{id}")
    fun getBlogById(@PathVariable id: Long) =
        blogsRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Blog with ID=$id not found.")
        }
}

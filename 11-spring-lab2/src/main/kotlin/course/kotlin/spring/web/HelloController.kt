package course.kotlin.spring.web

import course.kotlin.spring.dao.BlogsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}

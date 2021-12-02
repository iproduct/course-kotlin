package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.Blog
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class BlogsController(
    private val blogsService: BlogsService,
    private val usersService: UsersService,
) {
    @GetMapping
    fun blog(model : ModelMap): String {
        model["title"] = "Kotlin Blogs"
        model["blogs"] = blogsService.findAll()
        log().debug("GET Blogs {}", blogsService.findAll())
        return "blogs"
    }
}

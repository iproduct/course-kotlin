package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.model.toBlogDetailsView
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/blogs")
class BlogsController(
    private val blogsService: BlogsService
) {
    @GetMapping
    fun getAllBlogs(model: ModelMap): String {
        model["title"] = "Kotlin Blogs"
        model["blogs"] = blogsService.findAll().map {
            it.toBlogDetailsView()
        }
        return "blogs"
    }
}

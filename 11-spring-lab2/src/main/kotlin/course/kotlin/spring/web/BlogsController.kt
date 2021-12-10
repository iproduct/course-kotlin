package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.impl.DEFAULT_AUTHOR_USERNAME
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.BlogCreateView
import course.kotlin.spring.model.User
import course.kotlin.spring.model.toBlogDetailsView
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal
import javax.persistence.EntityNotFoundException

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

    @GetMapping("/blog-form")
    fun getAddEditBlogForm(
        model: ModelMap,
        @RequestParam(value = "mode", required = false) mode: String?,
        @RequestParam(value = "blogId", required = false) blogId: Long?,
        principal: Principal?
    ): String {
        model["title"] = if (mode != null && mode == "edit" && blogId != null) "Edit Blog"
        else "Add New Blog"
        if (!model.containsAttribute("blog")) {
            model["blog"] = if (mode != null && mode == "edit" && blogId != null)
                blogsService.findById(blogId)
            else
                BlogCreateView("", "")
        }
        model["loggedUserName"] = principal?.name
        model["path"]= ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().path
        return "blog-form"
    }
}

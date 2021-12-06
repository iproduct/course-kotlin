package course.kotlin.spring.web

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@Controller
@RequestMapping("/")
class BlogsController(
    private val blogsService: BlogsService,
    private val usersRepository: UsersRepository,
) {
    @GetMapping
    fun blog(model: ModelMap): String {
        model["title"] = "Kotlin Blogs"
        model["blogs"] = blogsService.findAll()
        log().debug("GET Blogs {}", blogsService.findAll())
        return "blogs"
    }

    @GetMapping("/blog-form")
    fun getBlogForm(
        model: ModelMap,
        @RequestParam(value = "mode", required = false) mode: String?,
        @RequestParam(value = "blogId", required = false) blogId: String?,
    ): String {
        var title = "Add New Blog"
        model["title"] = title
        model["path"] = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().path
        val authentication = SecurityContextHolder.getContext().authentication;
        if (!(authentication is AnonymousAuthenticationToken)) {
            val currentUserName = authentication.getName();
            model["loggedUserName"] = currentUserName
            val user = usersRepository.findByUsername(currentUserName)
            model["blog"] = Blog("", "", user!!)
            return "blog-form"
        }
        return "blogs"
    }
}

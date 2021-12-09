package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.extensions.log
import course.kotlin.spring.extensions.toModel
import course.kotlin.spring.model.BlogCreateView
import course.kotlin.spring.model.User
import course.kotlin.spring.model.toBlogDetailsView
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import javax.validation.Valid

@Controller
@RequestMapping("/blogs")
class BlogsController(
    private val blogsService: BlogsService,
    private val usersService: UsersService,
) {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateFormat.isLenient = false
        binder.registerCustomEditor(LocalDateTime::class.java, CustomDateEditor(dateFormat, false))
    }

    @GetMapping
    fun blog(model: ModelMap): String {
        model["title"] = "Kotlin Blogs"
        model["blogs"] = blogsService.findAll().map{
            it.toBlogDetailsView()
        }
        log().debug("GET Blogs {}", blogsService.findAll())
        return "blogs"
    }

    @GetMapping("/blog-form")
    fun getBlogForm(
        model: Model,
        @RequestParam(value = "mode", required = false) mode: String?,
        @RequestParam(value = "blogId", required = false) blogId: String?,
    ): String {
        var title = "Add New Blog"
        model.addAttribute("title", title)
        model.addAttribute("path", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().path)
        val authentication = SecurityContextHolder.getContext().authentication;
        var user: User? = null
        if (authentication != null && !(authentication is AnonymousAuthenticationToken)) {
            val currentUserName = authentication.getName();
            model.addAttribute("loggedUserName", currentUserName)
            user = usersService.findByUsername(currentUserName)
        }
        if (authentication == null || user == null) {
            user = usersService.findAll().first()
        }
        if(!model.containsAttribute("blog")) {
            model.addAttribute("blog", BlogCreateView("", ""))
        }
        return "blog-form"
    }

    @PostMapping("/blog-form")
    fun addEditBlog(@Valid @ModelAttribute("blog") blog:  BlogCreateView,
                    bindingResult: BindingResult,
                    redirectAttr: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "blog", bindingResult)
            redirectAttr.addFlashAttribute("blog", blog)
            redirectAttr.addFlashAttribute("errorMessages", bindingResult.allErrors)
            return "redirect:/blogs/blog-form"
        }
        val created = blogsService.create(blog.toModel())
        return "redirect:/blogs"
    }
}

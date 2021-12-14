package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.impl.DEFAULT_AUTHOR_USERNAME
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.*
import course.kotlin.spring.properties.BlogProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

@Controller
@RequestMapping("/blogs")
class BlogsController(
    private val blogsService: BlogsService,
    private val blogProperties: BlogProperties
) {
//    @field:Value("#{blog.title}")
//    private lateinit var title : String


    @GetMapping
    fun getAllBlogs(model: ModelMap): String {
        model["title"] = blogProperties.title
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
        model["title"] =  if (mode != null && mode == "edit" && blogId != null) "Edit Blog"
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

    @PostMapping("/blog-form")
    fun addEditBlog(@Valid @ModelAttribute blog: BlogCreateView,
                    bindingResult: BindingResult,
                    principal: Principal?,
                    redirectAttributes: RedirectAttributes
    ): String {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("blog", blog)
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "blog", bindingResult)
            return "redirect:blog-form"
        }
        if(blog.id != null) {    // edit existing blog
            log().info("Updating blog: '${blog.title}'")
            blogsService.update(blog.toBlog())
        } else {  // create new blog
            log().info("Creating new blog: '${blog.title}'")
            blogsService.create(blog.toBlog())
        }
        return "redirect:/blogs"
    }
}

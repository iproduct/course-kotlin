package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.entities.Blog
import course.kotlin.spring.extensions.log
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.regex.Pattern
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Controller
@RequestMapping("/blogs")
class BlogsController(val blogsService: BlogsService, val usersService: UsersService) {

    @GetMapping
    fun blog(model: Model): String {
        model.addAttribute("title", "Kotlin Blogs")
        model.addAttribute("path", "blogs")
        model.addAttribute("blogs", blogsService.findAll())
        log().debug("GET Blogs: {}", blogsService.findAll())
        return "blogs"
    }

    @GetMapping("/blog-form")
    fun getBlogForm(
        model: Model,
        @RequestParam(value = "mode", required = false) mode: String?,
        @RequestParam(value = "blogId", required = false) blogId: Long?
    ): String {
        if (model.getAttribute("blog") == null) {
            model.addAttribute("blog", Blog(null, "", "", usersService.findAll().first()))
        }
        var title = "Add New Blog"
        if ("edit" == mode) {
            val found: Blog = blogsService.findById(blogId!!)
            model.addAttribute("blog", found)
            title = "Edit Blog"
        }
        model.addAttribute("title", title)
        model.addAttribute("path", ServletUriComponentsBuilder.fromCurrentRequestUri().build().path)
        return "blog-form"
    }

    @PostMapping("/blog-form")
    fun addBlog(
        @ModelAttribute("blog") blog: @Valid Blog,
        errors: BindingResult,
        @RequestParam("file") file: MultipartFile,
        model: Model,
//        auth: @NotNull org.springframework.security.core.Authentication?
    ): String {
//        val author: User = auth.getPrincipal() as User
        model.addAttribute("fileError", null)
//        if (!file.isEmpty && file.originalFilename!!.length > 4) {
//            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.originalFilename)) {
//                handleFile(file, blog)
//            } else {
//                model.addAttribute("fileError", "Submit PNG or JPG picture please!")
//                return "blog-form"
//            }
//        }
//        if (author == null) {
//            errors.addError(ObjectError("blog", "No authenticated author"))
//            return "blog-form"
//        }
        if (errors.hasErrors()) {
            return "blog-form"
        }
//        blog.setAuthorId(author.getId())
//        blog.setAuthorName(author.getFullName())
        if (blog.id == null) {  // create
            log().info("Create new blog: {}", blog)
            blogsService.add(blog)
        } else { //edit
            log().info("Edit blog: {}", blog)
            blogsService.update(blog)
        }
        return "redirect:/blogs"
    }

}



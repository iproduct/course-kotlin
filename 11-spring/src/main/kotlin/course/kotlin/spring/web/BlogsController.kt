package course.kotlin.spring.web

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.BlogCreateView
import course.kotlin.spring.entities.BlogDetailsView
import course.kotlin.spring.extensions.log
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

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
            val found: BlogDetailsView = blogsService.findById(blogId!!)
            model.addAttribute("blog", found)
            title = "Edit Blog"
        }
        model.addAttribute("title", title)
        model.addAttribute("path", ServletUriComponentsBuilder.fromCurrentRequestUri().build().path)
        return "blog-form"
    }

    @PostMapping("/blog-form")
    fun addBlog(
        @ModelAttribute("blog") blogCreateView: @Valid BlogCreateView,
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
        if (blogCreateView.id == null) {  // create
            log().info("Create new blog: {}", blogCreateView)
            blogsService.add(blogCreateView)
        } else { //edit
            log().info("Edit blog: {}", blogCreateView)
            blogsService.update(blogCreateView)
        }
        return "redirect:/blogs"
    }

}



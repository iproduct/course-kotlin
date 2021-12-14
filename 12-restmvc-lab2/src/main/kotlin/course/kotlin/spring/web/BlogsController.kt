package course.kotlin.spring.web

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.model.BlogCreateView
import course.kotlin.spring.model.BlogDetailsView
import course.kotlin.spring.model.toBlog
import course.kotlin.spring.model.toBlogDetailsView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/blogs")
class BlogsController(
    private val blogsService: BlogsService
) {

    @GetMapping
    fun getBlogs() = blogsService.findAll().map{
        it.toBlogDetailsView()
    }

    @GetMapping("/{id}")
    fun getBlogById(@PathVariable id: Long) =
        blogsService.findById(id).toBlogDetailsView()

    @PostMapping
    fun createBlog(@RequestBody blog: BlogCreateView) =
        blogsService.create(blog.toBlog()).toBlogDetailsView()

    @PutMapping("/{id}")
    fun updateBlogById(@RequestBody blog: BlogCreateView, @PathVariable id: Long): BlogDetailsView {
        if (id != blog.id) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Ids in URL=$id and entity body=${blog.id} do not match.")
        }
        return blogsService.update(blog.toBlog()).toBlogDetailsView()
    }

    @DeleteMapping("/{id}")
    fun deleteBlogById(@PathVariable id: Long) =
        blogsService.deleteById(id).toBlogDetailsView()
}

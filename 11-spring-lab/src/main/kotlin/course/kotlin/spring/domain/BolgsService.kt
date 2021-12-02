package course.kotlin.spring.domain

import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.BlogCreateView
import course.kotlin.spring.model.BlogDetailsView

interface BlogsService {
    fun findAll(): List<BlogDetailsView>
    fun findById(id: Long): BlogDetailsView?
    fun findBySlug(slug: String): BlogDetailsView?
    fun create(blog: BlogCreateView): BlogDetailsView
    fun update(blog: BlogCreateView): BlogDetailsView
    fun delete(id: Long): BlogDetailsView
    fun count(): Long
}

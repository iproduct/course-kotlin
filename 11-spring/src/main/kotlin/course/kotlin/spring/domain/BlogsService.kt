package course.kotlin.spring.domain

import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.BlogCreateView
import course.kotlin.spring.entities.BlogDetailsView
import org.springframework.stereotype.Service

@Service
interface BlogsService {
    fun findAll(): Iterable<BlogDetailsView>
    fun findById(id: Long): BlogDetailsView
    fun add(blog: BlogCreateView): BlogDetailsView
    fun update(blog: BlogCreateView): BlogDetailsView
    fun removeById(id: Long): BlogDetailsView
    fun count(): Long
}

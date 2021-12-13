package course.kotlin.spring.domain

import course.kotlin.restmvc.model.Blog
import org.springframework.stereotype.Service

@Service
interface BlogsService {
    fun findAll(): List<Blog>
    fun findById(id: Long): Blog
    fun findBySlug(slug: String): Blog
    fun create(blog: Blog): Blog
    fun update(blog: Blog): Blog
    fun deleteById(id: Long): Blog
    fun count(): Long
}

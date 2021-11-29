package course.kotlin.spring.domain

import course.kotlin.spring.entities.Blog
import org.springframework.stereotype.Service

@Service
interface BlogsService {
    fun findAll(): Iterable<Blog>
    fun findById(id: Long): Blog
    fun add(blog: Blog): Blog
    fun update(blog: Blog): Blog
    fun removeById(id: Long): Blog
    fun count(): Long
}

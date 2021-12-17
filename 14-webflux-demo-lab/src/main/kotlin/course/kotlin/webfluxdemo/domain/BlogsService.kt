package course.kotlin.webfluxdemo.domain

import course.kotlin.spring.model.Blog
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BlogsService {
    fun findAll(): Flow<Blog>
    suspend fun findById(id: String): Blog
    suspend fun findBySlug(slug: String): Blog
    suspend fun create(blog: Blog): Blog
    suspend fun update(blog: Blog): Blog
    suspend fun deleteById(id: String): Blog
    suspend fun count(): Long
}

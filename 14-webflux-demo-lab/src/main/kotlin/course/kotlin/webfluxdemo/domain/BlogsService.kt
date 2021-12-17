package course.kotlin.webfluxdemo.domain

import course.kotlin.spring.model.Blog
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BlogsService {
    fun findAll(): Flux<Blog>
    fun findById(id: Long): Mono<Blog>
    fun findBySlug(slug: String): Blog
    fun create(blog: Blog): Blog
    fun update(blog: Blog): Blog
    fun deleteById(id: Long): Blog
    fun count(): Long
}

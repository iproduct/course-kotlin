package course.kotlin.webfluxdemo.dao

import course.kotlin.spring.model.Blog
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BlogsRepository : ReactiveMongoRepository<Blog, String> {
    fun findBySlug(slug: String): Blog?
    fun findAllByOrderByCreatedDesc(): List<Blog>
}

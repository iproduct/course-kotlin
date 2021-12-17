package course.kotlin.webfluxdemo.dao

import course.kotlin.spring.model.Blog
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BlogsRepository : ReactiveMongoRepository<Blog, String> {
    fun findBySlug(slug: String): Mono<Blog>
    fun findAllByOrderByCreatedDesc(): Flux<Blog>
}

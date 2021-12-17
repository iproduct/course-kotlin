package course.kotlin.webfluxdemo.dao

import course.kotlin.spring.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UsersRepository : ReactiveMongoRepository<User, String> {
    fun findByUsername(username: String): Mono<User>
}

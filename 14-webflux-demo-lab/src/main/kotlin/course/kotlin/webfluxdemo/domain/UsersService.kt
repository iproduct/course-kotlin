package course.kotlin.webfluxdemo.domain

import course.kotlin.spring.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UsersService {
    fun findAll(): Flux<User>
    fun findById(id: String): Mono<User>
    fun findByUsername(username: String): Mono<User>
    fun create(user: User): Mono<User>
    fun update(user: User): Mono<User>
    fun deleteById(id: String): Mono<User>
    fun count(): Mono<Long>
}

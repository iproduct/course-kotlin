package course.kotlin.webfluxdemo.web

import course.kotlin.spring.model.User
import course.kotlin.webfluxdemo.dao.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users2")
public class UsersControllerUsingRepo(private val usersRepository: UsersRepository) {

    @GetMapping
    fun findAll(): Flux<User> {
        return usersRepository.findAll();
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): Mono<User> {
        return usersRepository.findById(id)
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID='$id' does not exist.")
                )
            )
    }

    @PostMapping
    fun save(@RequestBody user: User): Mono<User> {
        return usersRepository.insert(user);
    }
}

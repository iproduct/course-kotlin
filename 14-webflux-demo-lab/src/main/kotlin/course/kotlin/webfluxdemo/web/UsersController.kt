package course.kotlin.webfluxdemo.web

import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.model.*
import course.kotlin.webfluxdemo.domain.UsersService
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
public class UsersController(private val usersService: UsersService) {

    @GetMapping
    fun findAll(): Flux<UserDetailsView> {
        return usersService.findAll().map { it.toUserDetailsView() };
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): Mono<UserDetailsView> {
        return usersService.findById(id).map { it.toUserDetailsView() }
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID='$id' does not exist.")
                )
            )
    }

    @PostMapping
    fun create(uriBuilder: UriComponentsBuilder, request: ServerHttpRequest, @RequestBody userCV: UserCreateView): Mono<ResponseEntity<UserDetailsView>> =
        usersService.create(userCV.toUser()).map { it.toUserDetailsView() }
            .map {
                ResponseEntity.created(
                    uriBuilder.path(request.path.toString())
                        .pathSegment("{id}").build(it.id)
                ).body(it)
            }


    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody userCV: UserCreateView): Mono<UserDetailsView> {
        if (id != userCV.id) {
            return Mono.error(InvalidEntityDataException("The ID='$id' in the URL and ID='${userCV.id} in the message body are different."))
        }
        return usersService.update(userCV.toUser()).map { it.toUserDetailsView() };
    }
}

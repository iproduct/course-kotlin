package course.kotlin.webfluxdemo.web

import course.kotlin.spring.extensions.isValidId
import course.kotlin.spring.model.*
import course.kotlin.webfluxdemo.domain.BlogsService
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException

@Component
public class BlogsHandler(private val blogsService: BlogsService) {
    suspend fun findAll(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyAndAwait(blogsService.findAll().map { it.toBlogDetailsView() })

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        if (!isValidId(id)) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Invalid URL blog id: $id")
        val found = blogsService.findById(id).toBlogDetailsView()
        return ServerResponse.ok().bodyValueAndAwait(found)
    }

//
//    @PostMapping
//    fun create(uriBuilder: UriComponentsBuilder, request: ServerHttpRequest, @RequestBody userCV: UserCreateView): Mono<ResponseEntity<UserDetailsView>> =
//        blogsService.create(userCV.toUser()).map { it.toUserDetailsView() }
//            .map {
//                ResponseEntity.created(
//                    uriBuilder.path(request.path.toString())
//                        .pathSegment("{id}").build(it.id)
//                ).body(it)
//            }
//
//
//    @PutMapping("/{id}")
//    fun update(@PathVariable id: String, @RequestBody userCV: UserCreateView): Mono<UserDetailsView> {
//        if (id != userCV.id) {
//            return Mono.error(InvalidEntityDataException("The ID='$id' in the URL and ID='${userCV.id} in the message body are different."))
//        }
//        return blogsService.update(userCV.toUser()).map { it.toUserDetailsView() };
//    }
}

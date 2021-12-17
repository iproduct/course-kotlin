package course.kotlin.webfluxdemo.web

import course.kotlin.spring.extensions.isValidId
import course.kotlin.spring.model.*
import course.kotlin.webfluxdemo.domain.BlogsService
import course.kotlin.webfluxdemo.domain.UsersService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException

@Component
public class UserWithDetailsHandler(private val blogsService: UsersService, private val client: WebClient) {
//    suspend fun findAll(request: ServerRequest): ServerResponse =
//        ServerResponse.ok().bodyAndAwait(blogsService.findAll().map { it.toBlogDetailsView() })

    suspend fun findUserWithDetailsById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        if (!isValidId(id)) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Invalid URL user id: $id")
        val user = blogsService.findById(id).awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(withDetails(user))
    }

    private suspend fun withDetails(user: User): UserWithDetails {
        val userDetail1 = client.get().uri("/api/users/userdetail1/${user.id}")
            .accept(APPLICATION_JSON)
            .awaitExchange { it.awaitBody<UserProjectExperience>() }
        val userDetail2 = client.get().uri("/api/users/userdetail2/${user.id}")
            .accept(APPLICATION_JSON)
            .awaitExchange { it.awaitBody<UserHRData>() }
        return UserWithDetails(user, userDetail1, userDetail2)
    }

}

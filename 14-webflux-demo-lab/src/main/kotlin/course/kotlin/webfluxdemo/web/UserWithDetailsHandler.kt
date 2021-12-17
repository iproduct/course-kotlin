package course.kotlin.webfluxdemo.web

import course.kotlin.spring.extensions.isValidId
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.*
import course.kotlin.webfluxdemo.domain.BlogsService
import course.kotlin.webfluxdemo.domain.UsersService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
public class UserWithDetailsHandler(private val usersService: UsersService, private val webClientBuilder: WebClient.Builder) {
    val webClient = webClientBuilder.baseUrl("http://localhost:8080").build();

    suspend fun findUserWithDetailsById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("userId")
        if (!isValidId(id)) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Invalid URL user id: $id")
        val user = usersService.findById(id).log().awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(withDetailsParallel(user))
    }

    private suspend fun withDetails(user: User): UserWithDetails {
        val userDetail1 = webClient.get().uri("/api/users/userdetails1/${user.id}")
            .accept(APPLICATION_JSON)
            .awaitExchange { it.awaitBody<UserProjectExperience>() }
        val userDetail2 = webClient.get().uri("/api/users/userdetails2/${user.id}")
            .accept(APPLICATION_JSON)
            .awaitExchange { it.awaitBody<UserHRData>() }
        return UserWithDetails(user, userDetail1, userDetail2)
    }

    private suspend fun withDetailsParallel(user: User): UserWithDetails = coroutineScope {
        val userDetail1 = async {
            webClient.get().uri("/api/users/userdetails1/${user.id}")
                .accept(APPLICATION_JSON)
                .awaitExchange { it.awaitBody<UserProjectExperience>() }
        }
        val userDetail2 = async {
            webClient.get().uri("/api/users/userdetails2/${user.id}")
                .accept(APPLICATION_JSON)
                .awaitExchange { it.awaitBody<UserHRData>() }
        }
        UserWithDetails(user, userDetail1.await(), userDetail2.await())
    }

}

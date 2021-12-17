package course.kotlin.webfluxdemo.web

import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UsersWithDetailsRouterConfig() {

    @Bean
    fun userWithDetailRoutes(handler: UserWithDetailsHandler) = coRouter {
        accept(APPLICATION_JSON).nest {
            GET("/api/users/{userId}/details", handler::findUserWithDetailsById)
        }
    }
}

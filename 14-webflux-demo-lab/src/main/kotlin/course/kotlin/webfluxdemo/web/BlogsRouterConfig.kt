package course.kotlin.webfluxdemo.web

import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BlogsRouterConfig() {

    @Bean
    fun productRoutes(handler: BlogsHandler) = coRouter {
        accept(APPLICATION_JSON).nest {
            GET("/api/blogs", handler::findAll)
            GET("/api/blogs/{id}", handler::findById)
        }
    }
}

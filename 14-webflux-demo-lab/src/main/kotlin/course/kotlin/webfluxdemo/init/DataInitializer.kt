package course.kotlin.webfluxdemo.init

import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.Role
import course.kotlin.spring.model.User
import course.kotlin.webfluxdemo.domain.BlogsService
import course.kotlin.webfluxdemo.domain.UsersService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class DataInitializer(
    private val blogsService: BlogsService,
    private val usersService: UsersService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        runBlocking {
            if (usersService.count().awaitSingle() == 0L) {
                val users = listOf(
                    User("Default", "Admin", "admin", "admin123&", Role.ADMIN),
                    User("Default", "Author", "author", "author123&", Role.AUTHOR),
                    User("Default", "Reader", "reader", "reader123&"),
                )
                println("Created default users: ${users.map { usersService.create(it).awaitSingle().name }}")
            }
            if (blogsService.count() == 0L) {
                val default_author = usersService.findAll().awaitFirst()
                val blogs = listOf(
                    Blog("Tile 1", "Content 1", default_author),
                    Blog("Tile 2", "Content 2", default_author),
                    Blog("Tile 3", "Content 3 ...", default_author)
                )
                println("Created sample blogs: ${blogs.map { blogsService.create(it).title }}")
            }
        }
    }
}

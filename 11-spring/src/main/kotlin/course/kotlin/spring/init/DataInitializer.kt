package course.kotlin.spring.init

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.Role
import course.kotlin.spring.entities.User
import course.kotlin.spring.extensions.log
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataInitializer(val blogsService: BlogsService, val usersService: UsersService): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        log().info("Initializing application...")
        if (usersService.count() == 0L) {
            var user = User(
                null, "Default", "Admin", "admin", "admin", Role.ADMIN,
                true, "/img/avatar.png", LocalDateTime.now(), LocalDateTime.now(),
            )
            log().info("Creating root admin user: {}", user.username)
            user = usersService.add(user)
        }
        if (blogsService.count() == 0L) {
            val author = usersService.findAll().first()
            var blogs = listOf(
                Blog(null, "New in Kotlin", "Multicatch is arriving soon ...", author),
                Blog(null, "Coroutines in Kotlin", "Coroutines are non-blocking and high performance ...", author),
                Blog(null, "Kotlin Flow", "Flow allows for non-blocking stream processing ...", author),
            )
            blogs = blogs.map(blogsService::add)
            log().info("Sample blogs created: $blogs")
        }
    }
}

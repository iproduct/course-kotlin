package course.kotlin.spring.init

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.BlogCreateView
import course.kotlin.spring.models.Role
import course.kotlin.spring.entities.User
import course.kotlin.spring.extensions.log
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataInitializer(val blogsRepository: BlogsRepository, val usersRepository: UsersRepository): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        log().info("Initializing application...")
        if (usersRepository.count() == 0L) {
            var user = User(
                null, "Default", "Admin", "admin", "admin", Role.ADMIN,
                true, "/img/avatar.png", LocalDateTime.now(), LocalDateTime.now(),
            )
            log().info("Creating root admin user: {}", user.username)
            user = usersRepository.save(user)
        }
        if (blogsRepository.count() == 0L) {
            val author = usersRepository.findAll().first()
            var blogs = listOf(
                Blog(null, "New in Kotlin", "Multicatch is arriving soon ...", author),
                Blog(null, "Coroutines in Kotlin", "Coroutines are non-blocking and high performance ...", author),
                Blog(null, "Kotlin Flow", "Flow allows for non-blocking stream processing ...", author),
            )
            val blogsDetails = blogs.map(blogsRepository::save)
            log().info("Sample blogs created: $blogsDetails")
        }
    }
}

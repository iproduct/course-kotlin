package course.kotlin.restmvc.init

import course.kotlin.restmvc.model.Blog
import course.kotlin.restmvc.model.User
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.Role
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val blogsService: BlogsService,
    private val usersService: UsersService
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        log().info("Initializing application ...")
        log().info("Initializing application ...")
        if (usersService.count() == 0L) {
            val user = User("Default", "Admin", "admin", "admin", Role.ADMIN)
            val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
            user.password = encoder.encode(user.getPassword())
            val created = usersService.create(user)
            log().info("Created user: ${created.username}")
        }
        if (blogsService.count() == 0L) {
            val defaultAuthor = usersService.findAll().first()
            val blogs = listOf(
                Blog("New in Kotlin", "Multicatch is arriving soon ...", defaultAuthor),
                Blog("Coroutines in Kotlin","Coroutines are non-blocking and high performance ...", defaultAuthor),
                Blog("Kotlin Flow", "Flow allows non-blocking stream processing ...", defaultAuthor),
            )
            val createdBlogs = blogs.map { blogsService.create(it) }
            log().info("Created blogs: $createdBlogs")
        }
    }
}

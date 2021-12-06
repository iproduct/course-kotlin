package course.kotlin.spring.init

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.Role
import course.kotlin.spring.model.User
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val blogsRepository: BlogsRepository,
    private val usersRepository: UsersRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        log().info("Initializing application ...")
        log().info("Initializing application ...")
        if (usersRepository.count() == 0L) {
            val user = User("Default", "Admin", "admin", "admin", Role.ADMIN)
            val created = usersRepository.save(user)
            log().info("Created user: ${created.username}")
        }
        if (blogsRepository.count() == 0L) {
            val defaultAuthor = usersRepository.findAll().first()
            val blogs = listOf(
                Blog("New in Kotlin", "Multicatch is arriving soon ...", defaultAuthor),
                Blog("Coroutines in Kotlin","Coroutines are non-blocking and high performance ...", defaultAuthor
                ),
                Blog("Kotlin Flow", "Flow allows non-blocking stream processing ...", defaultAuthor),
            )
            val createdBlogs = blogs.map { blogsRepository.save(it) }
            log().info("Created blogs: ${createdBlogs}")
        }
    }
}

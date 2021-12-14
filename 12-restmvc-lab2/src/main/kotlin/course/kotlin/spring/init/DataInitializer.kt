package course.kotlin.spring.init

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.Role
import course.kotlin.spring.model.User
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class DataInitializer  (
    private val usersService: UsersService,
    private val blogsService: BlogsService,
):  ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        if(usersService.count() == 0L) {
            val users = listOf(
                User("Default", "Admin", "admin", "admin123&", Role.ADMIN),
                User("Default", "Author", "author", "author123&", Role.AUTHOR),
                User("Default", "Reader", "reader", "reader123&"),
            )
            println("Created default users: ${users.map {usersService.create(it).name }}")
        }
        if(blogsService.count() == 0L) {
            val default_author = usersService.findAll().first()
            val blogs = listOf(
                Blog("Tile 1", "Content 1", default_author),
                Blog("Tile 2", "Content 2", default_author),
                Blog("Tile 3", "Content 3 ...", default_author)
            )
            println("Created sample blogs: ${blogs.map{ blogsService.create(it).title }}")
        }
    }
}

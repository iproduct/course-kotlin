package course.kotlin.spring.init

import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
//@Profile("!test")
class DataInitializer  (
    private val usersService: UsersService,
    private val blogsService: BlogsService,
):  ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        if(usersService.count() == 0L) {
            val user = User("Default", "Admin", "admin", "admin123&")
            println("Created default user ${usersService.create(user).name}")
        }
        if(blogsService.count() == 0L) {
            val default_author = usersService.findAll().first()
            val blogs = listOf(
                Blog("Tile 1", "Content 1", default_author)
            )
            println("Created default user ${blogs.map{  blogsService::create  }}")
        }
    }
}

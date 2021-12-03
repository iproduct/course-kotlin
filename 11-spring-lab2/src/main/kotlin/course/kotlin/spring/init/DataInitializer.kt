package course.kotlin.spring.init

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer  (
    private val usersRepository: UsersRepository,
    private val blogsRepository: BlogsRepository,
):  ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        if(usersRepository.count() == 0L) {
            val user = User(null, "Default", "Admin", "admin", "admin123&")
            println("Created default user ${usersRepository.save(user).name}")
        }
        if(blogsRepository.count() == 0L) {
            val default_author = usersRepository.findAll().first()
            val blogs = listOf(
                Blog(null, "Tile 1", "Content 1", default_author)
            )
            println("Created default user ${blogsRepository.saveAll(blogs)}")
        }
    }
}

package course.kotlin.spring.dao

import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.User
import org.springframework.data.repository.CrudRepository

interface BlogsRepository : CrudRepository<Blog, Long> {
    fun findBySlug(slug: String): Blog?
    fun findAllByOrderByCreatedDesc(): Iterable<Blog>
}

interface UsersRepository : CrudRepository<User, Long> {
    fun findByUsername(login: String): User?
}

package course.kotlin.restmvc.dao

import course.kotlin.restmvc.model.Blog
import course.kotlin.restmvc.model.User
import org.springframework.data.jpa.repository.JpaRepository


interface BlogsRepository : JpaRepository<Blog, Long> {
    fun findBySlug(slug: String): Blog?
    fun findAllByOrderByCreatedDesc(): List<Blog>
}

interface UsersRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}

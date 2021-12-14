package course.kotlin.spring.dao

import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}

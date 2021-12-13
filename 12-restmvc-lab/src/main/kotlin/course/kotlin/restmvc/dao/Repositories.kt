package course.kotlin.restmvc.dao

import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface BlogsRepository: JpaRepository<Blog, Long> {
    fun findBySlug(slug: String): Blog?
    fun findAllByOrderByCreatedDesc(): List<Blog>
}

@Repository
interface UsersRepository: JpaRepository<User, Long>{
    fun findByUsername(username: String): User?
}

package course.kotlin.spring.dao

import course.kotlin.spring.model.Blog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface BlogsRepository : JpaRepository<Blog, Long> {
    fun findBySlug(slug: String): Blog?
    fun findAllByOrderByCreatedDesc(): List<Blog>
}

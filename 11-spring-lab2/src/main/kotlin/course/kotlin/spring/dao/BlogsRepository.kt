package course.kotlin.spring.dao

import course.kotlin.spring.model.Blog
import org.springframework.data.jpa.repository.JpaRepository

interface BlogsRepository : JpaRepository<Blog, Long> {
}

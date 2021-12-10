package course.kotlin.spring.dao

import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
//@AutoConfigureTestDatabase (replace=AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val usersRepository: UsersRepository,
    val blogsRepository: BlogsRepository) {

    @Test
    fun `Given Blog When findByIdOrNull then return Blog`() {
        val juergen = User("Juergen", "Hoeller", "springjuergen", "juergen123&")
        entityManager.persist(juergen)
        val blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
        entityManager.persist(blog)
        entityManager.flush()
        val found = blogsRepository.findByIdOrNull(blog.id!!)
        assertThat(found).isEqualTo(blog)
    }
    @Test
    fun `Given Blog When findBySlug then return Blog`() {
        val juergen = User("Juergen", "Hoeller", "springjuergen", "juergen123&")
        entityManager.persist(juergen)
        val blog = Blog("Spring Framework", "Dear Spring community ...", juergen)
        entityManager.persist(blog)
        entityManager.flush()
        val found = blogsRepository.findBySlug("spring-framework")
        assertThat(found).isEqualTo(blog)
    }
    @Test
    fun `Given User When findByUsername then return User`() {
        val juergen = User("Juergen", "Hoeller", "springjuergen", "juergen123&")
        entityManager.persist(juergen)
        entityManager.flush()
        val user = usersRepository.findByUsername(juergen.username)
        assertThat(user).isEqualTo(juergen)
    }
}

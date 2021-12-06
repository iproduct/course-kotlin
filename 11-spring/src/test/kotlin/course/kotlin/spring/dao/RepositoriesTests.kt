package course.kotlin.spring.dao

import course.kotlin.spring.entities.Blog
import course.kotlin.spring.entities.User
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UsersRepository,
    val blogsRepository: BlogsRepository) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val juergen = User(null, "Juergen", "Hoeller","springjuergen" )
        entityManager.persist(juergen)
        val article = Blog(null, "Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
        entityManager.persist(article)
        entityManager.flush()
        val found = blogsRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val juergen = User(null, "Juergen", "Hoeller","springjuergen" )
        entityManager.persist(juergen)
        entityManager.flush()
        val user = userRepository.findByUsername((juergen.username) )
        assertThat(user).isEqualTo(juergen)
    }
}

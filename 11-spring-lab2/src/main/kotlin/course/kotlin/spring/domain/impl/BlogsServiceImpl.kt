package course.kotlin.spring.domain.impl

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.model.Blog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class BlogsServiceImpl(
    private val blogsRepository: BlogsRepository,
    private val usersRepository: UsersRepository
) : BlogsService {
//    @Autowired
//    lateinit var usersRepository: UsersRepository

    override fun findAll(): List<Blog> = blogsRepository.findAll()

    override fun findById(id: Long): Blog =
        blogsRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Blod with ID=$id not found.")
        }

    override fun findBySlug(slug: String): Blog =
        blogsRepository.findBySlug(slug).orElseThrow {
            throw EntityNotFoundException("Blod with ID=$id not found.")
        }

    override fun create(blog: Blog): Blog {
        TODO("Not yet implemented")
    }

    override fun update(blog: Blog): Blog {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): Blog {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}

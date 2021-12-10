package course.kotlin.spring.domain.impl

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.exception.UnauthorisedException
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

val DEFAULT_AUTHOR_USERNAME = "author"

@Service
class BlogsServiceImpl(
    private val blogsRepository: BlogsRepository,
    private val usersRepository: UsersRepository
) : BlogsService {
//    @Autowired
//    lateinit var usersRepository: UsersRepository

    override fun findAll(): List<Blog> = blogsRepository.findAllByOrderByCreatedDesc()

    override fun findById(id: Long): Blog =
        blogsRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Blog with ID=$id not found.")
        }

    override fun findBySlug(slug: String): Blog =
        blogsRepository.findBySlug(slug) ?: throw EntityNotFoundException("Blog with ID=$id not found.")


    override fun create(blog: Blog): Blog {
        val authentication = SecurityContextHolder.getContext().authentication;
        var user: User? = null
        if (authentication == null) {
            user = usersRepository.findByUsername(DEFAULT_AUTHOR_USERNAME)
        } else if (!(authentication is AnonymousAuthenticationToken)) {
            user = usersRepository.findByUsername(authentication.getName())
        }
        if (user != null) {
            blog.author = user
            blogsRepository.findBySlug(blog.slug) ?: return blogsRepository.save(blog)
            throw InvalidEntityDataException("Slug '${blog.slug}' is already taken. ")
        }
        throw UnauthorisedException("The user should logged in to create blogs")
    }

    override fun update(blog: Blog): Blog {
        val old = blog.id?.let {
            blogsRepository.findById(it).orElseThrow {
                throw EntityNotFoundException("Blog with ID=${blog.id} not found.")
            }
        } ?: throw InvalidEntityDataException("Undefined blog ID.")
        if (blog.author != old.author)
            throw InvalidEntityDataException("The blog author could not be changed.")
        blog.modified = LocalDateTime.now()
        blog.created = old.created
        return blogsRepository.save(blog)
    }

    override fun deleteById(id: Long): Blog {
        val old = blogsRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Blog with ID=$id not found.")
        }
        blogsRepository.deleteById(id)
        return old;
    }

    override fun count(): Long = blogsRepository.count()
}

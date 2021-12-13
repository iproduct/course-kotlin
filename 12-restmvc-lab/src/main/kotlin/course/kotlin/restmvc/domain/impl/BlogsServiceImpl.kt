package course.kotlin.restmvc.domain.impl

import course.kotlin.restmvc.dao.BlogsRepository
import course.kotlin.restmvc.dao.UsersRepository
import course.kotlin.restmvc.model.Blog
import course.kotlin.restmvc.model.User
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.exception.UnauthorisedException
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
            throw course.kotlin.spring.exception.EntityNotFoundException("Blog with ID=$id not found.")
        }

    override fun findBySlug(slug: String): Blog =
        blogsRepository.findBySlug(slug) ?: throw course.kotlin.spring.exception.EntityNotFoundException("Blog with ID=$id not found.")


    override fun create(blog: Blog): Blog {
        val authentication = SecurityContextHolder.getContext().authentication;
        var user: User? = null
        if (authentication != null && !(authentication is AnonymousAuthenticationToken)) {
            val currentUserName = authentication.getName();
            user = usersRepository.findByUsername(currentUserName)
        }
        if (authentication == null || user == null) {
            user = usersRepository.findAll().first()
        }
        user?.let {
            blog.author = it
        }?: throw UnauthorisedException("The user should logged in to create blogs")
        blog.created = LocalDateTime.now()
        blog.modified = LocalDateTime.now()
        blogsRepository.findBySlug(blog.slug)?.let{
            throw InvalidEntityDataException("Slug '${blog.slug}' is already taken. ")
        }
        return blogsRepository.save(blog)
    }

    override fun update(blog: Blog): Blog {
        val old = blog.id?.let {
            blogsRepository.findById(it).orElseThrow {
                throw course.kotlin.spring.exception.EntityNotFoundException("Blog with ID=${blog.id} not found.")
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
            throw course.kotlin.spring.exception.EntityNotFoundException("Blog with ID=$id not found.")
        }
        blogsRepository.deleteById(id)
        return old;
    }

    override fun count(): Long = blogsRepository.count()
}

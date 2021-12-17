package course.kotlin.webfluxdemo.domain.impl

import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.exception.UnauthorisedException
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import course.kotlin.webfluxdemo.dao.BlogsRepository
import course.kotlin.webfluxdemo.dao.UsersRepository
import course.kotlin.webfluxdemo.domain.BlogsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
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

    override fun findAll(): Flow<Blog> = blogsRepository.findAll().asFlow()

    override suspend fun findById(id: String): Blog =
        blogsRepository.findById(id).awaitFirstOrElse { throw EntityNotFoundException("Blog with ID=$id not found.") }

    override suspend fun findBySlug(slug: String): Blog =
        blogsRepository.findBySlug(slug).awaitFirstOrElse { throw EntityNotFoundException("Blog with slug=$slug not found.")}

    override suspend fun create(blog: Blog): Blog {
        val authentication = SecurityContextHolder.getContext().authentication;
        var user: User? = null
        if (authentication == null || authentication is AnonymousAuthenticationToken) {
            user = usersRepository.findByUsername(DEFAULT_AUTHOR_USERNAME).awaitSingleOrNull()
        } else {
            user = usersRepository.findByUsername(authentication.getName()).awaitSingleOrNull()
        }
        if (user != null) {
            val newBlog = blog.copy(author = user)
            if(blogsRepository.findBySlug(newBlog.slug).awaitFirstOrNull() == null) {
                return blogsRepository.save(blog).awaitSingle()
            } else {
                throw InvalidEntityDataException("Slug '${blog.slug}' is already taken. ")
            }
        } else {
            throw UnauthorisedException("The user should logged in to create blogs")
        }
    }

    override suspend fun update(blog: Blog): Blog {
        val old = blog.id?.let {
            findById(it)
        } ?: throw InvalidEntityDataException("Undefined blog ID.")
        if (blog.author != null && blog.author != old.author)
            throw InvalidEntityDataException("The blog author could not be changed.")
        val updatedBlog = blog.copy(author = old.author, created = old.created, modified = LocalDateTime.now())
        return blogsRepository.save(blog).awaitSingle()
    }

    override suspend fun deleteById(id: String): Blog {
        val oldBlog = findById(id)
        blogsRepository.deleteById(id)
        return oldBlog;
    }

    override suspend fun count(): Long = blogsRepository.count().awaitSingle()
}

package course.kotlin.spring.domain

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.entities.Blog
import course.kotlin.spring.exception.NonexistingEntityException
import course.kotlin.spring.exception.UnauthorisedModificationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BlogsServiceImpl(
    private val blogsRepository: BlogsRepository,
    private val usersRepository: UsersRepository,
) : BlogsService {
//    @Autowired
//    private val blogsRepository: BlogsRepository? = null
//    @Autowired
//    private val usersRepository: UsersRepository? = null

    override fun findAll(): Iterable<Blog> {
        return blogsRepository.findAll()
    }

    override fun findById(blogId: Long): Blog {
        return blogsRepository.findByIdOrNull(blogId)?: 
            throw NonexistingEntityException("Blog with ID=$blogId does not exist.")
    }

    override fun add(blog: Blog): Blog {
        return blogsRepository.save(blog)
    }

//    @PreAuthorize("(#blog.authorId == authentication.principal.id) or hasRole('ADMIN')")
    override fun update(blog: Blog): Blog {
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: Blog = findById(blog.id!!)
//        if (user == null ||
//            !authentication.getAuthorities().contains(SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId()
//                .equals(user.getId())
//        ) {
//            throw UnauthorisedModificationException("You have no permissions to edit blog: " + old.getTitle())
//        }
        blog.created = old.created
        blog.modified = LocalDateTime.now()
        return blogsRepository.save(blog)
    }

    override fun removeById(blogId: Long): Blog {
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: Blog = findById(blogId)
//        if (user == null ||
//            !authentication.getAuthorities().contains(SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId()
//                .equals(user.getId())
//        ) {
//            throw UnauthorisedModificationException("You have no permissions to delete blog: " + old.getTitle())
//        }
        blogsRepository.deleteById(blogId)
        return old
    }

    override fun count(): Long {
        return blogsRepository.count()
    }

}

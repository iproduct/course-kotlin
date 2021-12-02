package course.kotlin.spring.domain

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.entities.*
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

    override fun findAll(): Iterable<BlogDetailsView> {
        return blogsRepository.findAll().map{it.toBlogDetailsViewReflection()}
    }

    override fun findById(blogId: Long): BlogDetailsView {
        return blogsRepository.findByIdOrNull(blogId)?.toBlogDetailsViewReflection()?:
            throw NonexistingEntityException("Blog with ID=$blogId does not exist.")
    }

    override fun add(blogCreateView: BlogCreateView): BlogDetailsView {
        val blog = blogCreateView.toBlogReflection()
        if(usersRepository.count() > 0) {
            blog.author = usersRepository.findAll().first()
        }
        return blogsRepository.save(blog).toBlogDetailsViewReflection()
    }

//    @PreAuthorize("(#blog.authorId == authentication.principal.id) or hasRole('ADMIN')")
    override fun update(blogCreateView: BlogCreateView): BlogDetailsView {
        val blog = blogCreateView.toBlogReflection()
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: BlogDetailsView = findById(blog.id!!)
//        if (user == null ||
//            !authentication.getAuthorities().contains(SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId()
//                .equals(user.getId())
//        ) {
//            throw UnauthorisedModificationException("You have no permissions to edit blog: " + old.getTitle())
//        }
        blog.created = old.created
        blog.modified = LocalDateTime.now()
        return blogsRepository.save(blog).toBlogDetailsViewReflection()
    }

    override fun removeById(blogId: Long): BlogDetailsView {
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: BlogDetailsView = findById(blogId)
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

package course.kotlin.spring.domain

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.entities.User
import course.kotlin.spring.exception.NonexistingEntityException
import course.kotlin.spring.exception.UnauthorisedModificationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
) : UsersService {
//    @Autowired
//    private val usersRepository: UsersRepository? = null
//    @Autowired
//    private val usersRepository: UsersRepository? = null

    override fun findAll(): Iterable<User> {
        return usersRepository.findAll()
    }

    override fun findById(userId: Long): User {
        return usersRepository.findByIdOrNull(userId)?: 
            throw NonexistingEntityException("User with ID=$userId does not exist.")
    }

    override fun add(user: User): User {
        return usersRepository.save(user)
    }

//    @PreAuthorize("(#user.authorId == authentication.principal.id) or hasRole('ADMIN')")
    override fun update(user: User): User {
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: User = findById(user.id!!)
//        if (user == null ||
//            !authentication.getAuthorities().contains(SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId()
//                .equals(user.getId())
//        ) {
//            throw UnauthorisedModificationException("You have no permissions to edit user: " + old.getTitle())
//        }
        user.created = old.created
        user.modified = LocalDateTime.now()
        return usersRepository.save(user)
    }

    override fun removeById(userId: Long): User {
//        val authentication: org.springframework.security.core.Authentication =
//            SecurityContextHolder.getContext().getAuthentication()
//        val user: User = authentication.getPrincipal() as User
        val old: User = findById(userId)
//        if (user == null ||
//            !authentication.getAuthorities().contains(SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId()
//                .equals(user.getId())
//        ) {
//            throw UnauthorisedModificationException("You have no permissions to delete user: " + old.getTitle())
//        }
        usersRepository.deleteById(userId)
        return old
    }

    override fun count(): Long {
        return usersRepository.count()
    }

}

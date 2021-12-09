package course.kotlin.spring.domain.impl

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.exception.UnauthorisedException
import course.kotlin.spring.model.Role
import course.kotlin.spring.model.User
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.security.RolesAllowed

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
) : UsersService {
//    @Autowired
//    lateinit var usersRepository: UsersRepository

    override fun findAll(): List<User> = usersRepository.findAll()

    override fun findById(id: Long): User =
        usersRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("User with ID=$id not found.")
        }

    override fun findByUsername(username: String): User =
        usersRepository.findByUsername(username) ?: throw EntityNotFoundException("User with ID=$id not found.")

    override fun create(user: User): User {
        return usersRepository.save(user)
    }

    override fun update(user: User): User {
        val old = user.id?.let {
            usersRepository.findById(it).orElseThrow {
                throw EntityNotFoundException("User with ID=${user.id} not found.")
            }
        } ?: throw InvalidEntityDataException("Undefined user ID.")
        if (user.username != old.username)
            throw InvalidEntityDataException("The user author could not be changed.")
        usersRepository.findByUsername(user.username)
            ?: throw InvalidEntityDataException("Username '${user.username}' is already taken. ")
        user.modified = LocalDateTime.now()
        user.created = old.created
        return usersRepository.save(user)
    }

    override fun deleteById(id: Long): User {
        val old = usersRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("User with ID=$id not found.")
        }
        usersRepository.deleteById(id)
        return old;
    }

    override fun count(): Long = usersRepository.count()
}

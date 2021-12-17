package course.kotlin.webfluxdemo.domain.impl

import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.model.User
import course.kotlin.webfluxdemo.dao.UsersRepository
import course.kotlin.webfluxdemo.domain.UsersService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
) : UsersService {

    override fun findAll() = usersRepository.findAll()

    override fun findById(id: String) =
        usersRepository.findById(id).switchIfEmpty(
            Mono.error(
                EntityNotFoundException("User with ID='$id' not found.")
            )
        )

    override fun findByUsername(username: String) =
        usersRepository.findByUsername(username).switchIfEmpty(
            Mono.error(
                EntityNotFoundException("User with username = '$username' not found.")
            )
        )

    override fun create(user: User): Mono<User> = Mono.just(user)
        .flatMap {
            usersRepository.findByUsername(it.username)
                .flatMap<User>({ Mono.error(InvalidEntityDataException("Username '${user.username}' is already taken. ")) })
                .switchIfEmpty(Mono.just(it))
        }.flatMap {
            val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
            val newUser= user.copy(
                password = encoder.encode(user.getPassword()),
                created = LocalDateTime.now(),
                modified = user.created
            )
            usersRepository.insert(newUser)
        }


override fun update(user: User): Mono<User> = Mono.just(user)
    .filter { it.id != null }
    .switchIfEmpty(Mono.error(InvalidEntityDataException("User id is not defined.")))
    .flatMap { usersRepository.findById(it.id!!) }
    .switchIfEmpty(Mono.error(EntityNotFoundException("User with username = '${user.id}' not found.")))
    .filter { it.username == user.username }
    .switchIfEmpty(Mono.error(InvalidEntityDataException("The username could not be changed.")))
    .flatMap<User> {
        val updatedUser = user.copy(modified = LocalDateTime.now(), created = it.created,
            password = user.password ?: it.password )
        usersRepository.save(updatedUser)
    }


override fun deleteById(id: String): Mono<User> =
    findById(id).flatMap { usersRepository.deleteById(id).thenReturn(it) }

override fun count() = usersRepository.count()
}

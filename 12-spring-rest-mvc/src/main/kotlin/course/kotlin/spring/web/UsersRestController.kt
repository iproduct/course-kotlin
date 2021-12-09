package course.kotlin.spring.web

import course.kotlin.spring.dao.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/users")
class UsersRestController(
    private val usersRepository: UsersRepository
) {

    @GetMapping
    @RequestMapping
    fun getUsers() = usersRepository.findAll()

    @GetMapping
    @RequestMapping("/{id}")
    fun getUserById(@PathVariable id: Long) =
        usersRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID=$id not found.")
        }
}

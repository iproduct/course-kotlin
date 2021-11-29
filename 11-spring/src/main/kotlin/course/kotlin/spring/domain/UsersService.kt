package course.kotlin.spring.domain

import course.kotlin.spring.entities.User
import org.springframework.stereotype.Service

@Service
interface UsersService {
    fun findAll(): Iterable<User>
    fun findById(id: Long): User
    fun add(user: User): User
    fun update(user: User): User
    fun removeById(id: Long): User
    fun count(): Long
}

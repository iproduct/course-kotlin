package course.kotlin.spring.domain

import course.kotlin.spring.model.User
import org.springframework.stereotype.Service

@Service
interface UsersService {
    fun findAll(): List<User>
    fun findById(id: Long): User
    fun findByUsername(username: String): User
    fun create(user: User): User
    fun update(user: User): User
    fun deleteById(id: Long): User
    fun count(): Long
}

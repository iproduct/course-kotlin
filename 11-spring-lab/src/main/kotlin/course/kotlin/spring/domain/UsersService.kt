package course.kotlin.spring.domain

import course.kotlin.spring.model.UserCreateView
import course.kotlin.spring.model.UserDetailsView

interface UsersService {
    fun findAll(): List<UserDetailsView>
    fun findById(id: Long): UserDetailsView?
    fun findBySlug(slug: String): UserDetailsView?
    fun create(blog: UserCreateView): UserDetailsView
    fun update(blog: UserCreateView): UserDetailsView
    fun delete(id: Long): UserDetailsView
    fun count(): Long
}

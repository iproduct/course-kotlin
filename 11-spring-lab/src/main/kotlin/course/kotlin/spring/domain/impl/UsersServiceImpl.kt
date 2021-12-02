package course.kotlin.spring.domain.impl

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.extensions.toModel
import course.kotlin.spring.model.UserCreateView
import course.kotlin.spring.model.UserDetailsView
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository
    ) : UsersService {
    override fun findAll(): List<UserDetailsView> =
        usersRepository.findAll().map{it.toModel(::UserDetailsView)}


    override fun findById(id: Long): UserDetailsView? {
        TODO("Not yet implemented")
    }

    override fun findBySlug(slug: String): UserDetailsView? {
        TODO("Not yet implemented")
    }

    override fun create(user: UserCreateView): UserDetailsView {
        TODO("Not yet implemented")
    }

    override fun update(user: UserCreateView): UserDetailsView {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): UserDetailsView {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}

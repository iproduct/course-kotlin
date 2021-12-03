package course.kotlin.spring.domain.impl

import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.BlogsService
import course.kotlin.spring.extensions.toModel
import course.kotlin.spring.model.BlogCreateView
import course.kotlin.spring.model.BlogDetailsView
import course.kotlin.spring.model.toBlogDetailsViewReflection
import org.springframework.stereotype.Service

@Service
class BlogsServiceImpl(
    private val blogsRepository: BlogsRepository,
    private val usersRepository: UsersRepository
    ) : BlogsService {
    override fun findAll(): List<BlogDetailsView> =
        blogsRepository.findAllByOrderByCreatedDesc().map{it.toModel(BlogDetailsView::class)} //map{it.toBlogDetailsViewReflection()}


    override fun findById(id: Long): BlogDetailsView? {
        TODO("Not yet implemented")
    }

    override fun findBySlug(slug: String): BlogDetailsView? {
        TODO("Not yet implemented")
    }

    override fun create(blog: BlogCreateView): BlogDetailsView {
        TODO("Not yet implemented")
    }

    override fun update(blog: BlogCreateView): BlogDetailsView {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): BlogDetailsView {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}

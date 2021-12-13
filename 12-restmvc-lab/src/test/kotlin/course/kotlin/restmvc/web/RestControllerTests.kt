package course.kotlin.restmvc.web

import com.ninjasquad.springmockk.MockkBean
import course.kotlin.restmvc.Application
import course.kotlin.restmvc.dao.BlogsRepository
import course.kotlin.restmvc.dao.UsersRepository
import course.kotlin.restmvc.model.Blog
import course.kotlin.restmvc.model.User
import course.kotlin.spring.model.BlogCreateView
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RestControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var blogsRepository: BlogsRepository

    @MockkBean
    private lateinit var usersRepository: UsersRepository

    @Test
    fun `List blogs`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&")
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/blogs/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].author.username").value(juergen.username))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].slug").value(spring5Blog.slug))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].author.username").value(juergen.username))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].slug").value(spring43Blog.slug))
    }

}

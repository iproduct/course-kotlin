package course.kotlin.spring.web

import com.ninjasquad.springmockk.MockkBean
import course.kotlin.spring.Application
import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.Role
import course.kotlin.spring.model.User
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RestControllerTestWebMvc(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var blogsRepository: BlogsRepository

    @MockkBean
    private lateinit var usersRepository: UsersRepository

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `List blogs`() {
        // setup
        val juergen = User("springjurgen", "Juergen", "Hoeller", "juergen1234&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        //test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/blogs").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].author.username").value(juergen.username))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].slug").value(spring5Blog.slug))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].author.username").value(juergen.username))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].slug").value(spring43Blog.slug))

        // verify repo method called
        verify { blogsRepository.findAll() }
    }

}

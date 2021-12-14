package course.kotlin.spring.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import course.kotlin.spring.Application
import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import course.kotlin.spring.model.toBlogDetailsView
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalToObject
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
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

    @Autowired
    private lateinit var mapper: ObjectMapper

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
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.length()").value(2))
            .andExpect(MockMvcResultMatchers.content().string(
               mapper.writeValueAsString(listOf(spring5Blog.toBlogDetailsView(),spring43Blog.toBlogDetailsView()))))

        // verify repo method called
        verify { blogsRepository.findAll() }
        confirmVerified(blogsRepository)  // no other methods were called
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `List blogs - Kotlin DSL`() {
        // setup
        val juergen = User("Juergen", "Hoeller", "springjurgen", "juergen1234&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        //test
        mockMvc.get("/api/blogs") {
            accept = MediaType.APPLICATION_JSON
        }
//            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("\$.[0].author.username") { value(juergen.username) }
                jsonPath("\$.[0].slug") { value(spring5Blog.slug) }
                jsonPath("\$.[1].author.username") { value(juergen.username) }
                jsonPath("\$.[1].slug") { value(spring43Blog.slug) }
                jsonPath("\$") { isArray() }
                jsonPath("\$.length()") { equalTo(2) }
                content {
                    json(mapper.writeValueAsString(listOf(spring5Blog.toBlogDetailsView(),spring43Blog.toBlogDetailsView())))
                }
            }
        // verify repo method called
        verify { blogsRepository.findAll() }
        confirmVerified(blogsRepository)  // no other methods were called

    }
}

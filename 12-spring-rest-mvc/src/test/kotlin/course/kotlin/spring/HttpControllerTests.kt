package course.kotlin.spring

import com.ninjasquad.springmockk.MockkBean
import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
//@EnableAutoConfiguration(exclude=[SecurityAutoConfiguration::class])
//@WebMvcTest
//@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HttpControllersTests(@Autowired val mockMvc: MockMvc,
                           @Autowired val usersRepo: UsersRepository,
                           @Autowired val blogsRepo: BlogsRepository) {

    @SpyK
    private var usersRepository: UsersRepository = usersRepo

    @SpyK
    private var blogsRepository: BlogsRepository = blogsRepo

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)


    @Test
    fun `List blogs`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&")
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen)
        every { blogsRepository.findAllByOrderByCreatedDesc() } returns listOf(spring5Blog, spring43Blog)
        mockMvc.perform(get("/api/blogs/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.username").value(juergen.username))
            .andExpect(jsonPath("\$.[0].slug").value(spring5Blog.slug))
            .andExpect(jsonPath("\$.[1].author.username").value(juergen.username))
            .andExpect(jsonPath("\$.[1].slug").value(spring43Blog.slug))
    }

    @Test
    fun `List users`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller","jurgen123&")
        val smaldini = User("smaldini", "Stéphane", "Maldini", "stephane123&")
        every { usersRepository.findAll() } returns listOf(juergen, smaldini)
        mockMvc.perform(get("/api/users/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].username").value(juergen.username))
            .andExpect(jsonPath("\$.[1].username").value(smaldini.username))
    }
}

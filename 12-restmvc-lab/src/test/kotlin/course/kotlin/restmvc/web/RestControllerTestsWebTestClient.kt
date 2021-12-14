package course.kotlin.restmvc.web

import com.ninjasquad.springmockk.MockkBean
import course.kotlin.restmvc.Application
import course.kotlin.restmvc.dao.BlogsRepository
import course.kotlin.restmvc.dao.UsersRepository
import course.kotlin.restmvc.model.Blog
import course.kotlin.restmvc.model.User
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.BlogDetailsView
import course.kotlin.spring.model.toBlogDetailsView
import io.mockk.MockKAnnotations
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@AutoConfigureWebTestClient
//@WebFluxTest(controllers = [BlogsRestController::class, UsersRestController::class, Application::class])
//@AutoConfigureWebTestClient
@ActiveProfiles("test")
class HttpControllersTestsWebTestClient() {
    @Autowired
    lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var usersRepository: UsersRepository

    @MockkBean
    private lateinit var blogsRepository: BlogsRepository

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    @WithMockUser(roles = ["ADMIN"])
    @Throws(Exception::class)
    fun givenArticles_whenGetArticles_thenStatus200andJsonArray() {
        val now = LocalDateTime.now();
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

//        var exchangeMutator: Any
        val response = webClient    //	.mutateWith(mockUser("admin").password("admin").roles("ADMIN"))
//            .mutate().defaultCookie(org.iproduct.spring.restmvc.RestMvcBootApplicationTests.AUTH_TOKEN, authToken)
//            .build()
            .get().uri("/api/blogs").accept(MediaType.APPLICATION_JSON)
            .exchange()

        response.expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("\$.[0].author.username").isEqualTo(juergen.username)
            .jsonPath("\$.[0].slug").isEqualTo(spring5Blog.slug)
            .jsonPath("\$.[1].author.username").isEqualTo(juergen.username)
            .jsonPath("\$.[1].slug").isEqualTo(spring43Blog.slug)
            .jsonPath("\$").isArray
            .jsonPath("\$").value<List<BlogDetailsView>> { log().info(">>> {}", it) }
            .jsonPath("\$.length()").value<Int> { log().info(">>>Length: {}", it) }
            .jsonPath("\$.length()").isEqualTo(2)
            .also {
                assertThat(
                    containsInAnyOrder(
                        it,
                        spring5Blog.toBlogDetailsView().copy(created = "111", modified = ""),
                        spring43Blog.toBlogDetailsView().copy(created = "", modified = ""),
                    )
                )
            }
//        response
//            .returnResult<List<BlogDetailsView>>().responseBody.subscribe { b ->
//                log().info(">>>{}", b)
//                b.map {
//                    it.copy(created = "", modified = "")
//                }.containsAll(
//                    listOf(
//                        spring5Blog.toBlogDetailsView().copy(created = "", modified = ""),
//                        spring43Blog.toBlogDetailsView().copy(created = "", modified = "")
//                    )
//                )
//            }

//        response.returnResult<String>().responseBody.subscribe {
//            log().info(">>> {}", it)
//        }
    }

//    @Test
//    fun `List blogs`() {
//        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&")
//        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
//        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen)
//        every { blogsRepository.findAllByOrderByCreatedDesc() } returns listOf(spring5Blog, spring43Blog)
//        webClient.perform(get("/api/blogs/").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("\$.[0].author.username").value(juergen.username))
//            .andExpect(jsonPath("\$.[0].slug").value(spring5Blog.slug))
//            .andExpect(jsonPath("\$.[1].author.username").value(juergen.username))
//            .andExpect(jsonPath("\$.[1].slug").value(spring43Blog.slug))
//    }
//
//    @Test
//    fun `List users`() {
//        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&")
//        val smaldini = User("smaldini", "Stéphane", "Maldini", "stephane123&")
//        every { usersRepository.findAll() } returns listOf(juergen, smaldini)
//        mockMvc.perform(get("/api/users/").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("\$.[0].username").value(juergen.username))
//            .andExpect(jsonPath("\$.[1].username").value(smaldini.username))
//    }
}

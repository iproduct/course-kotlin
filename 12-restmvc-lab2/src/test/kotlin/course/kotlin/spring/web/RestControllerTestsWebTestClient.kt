package course.kotlin.spring.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import course.kotlin.spring.Application
import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.extensions.log
import course.kotlin.spring.extensions.typeReference
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.BlogDetailsView
import course.kotlin.spring.model.User
import course.kotlin.spring.model.toBlogDetailsView
import io.mockk.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import java.time.LocalDateTime
import java.util.function.Consumer


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
//@AutoConfigureWebTestClient
//@WebFluxTest(controllers = [BlogsRestController::class, UsersRestController::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpControllersTestsWebTestClient(@Autowired private val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var usersRepository: UsersRepository

    @MockkBean
    private lateinit var blogsRepository: BlogsRepository

    lateinit var webClient: WebTestClient

    @Autowired
    private lateinit var mapper: ObjectMapper

//    @BeforeEach
//    fun setUp() = MockKAnnotations.init(this)

    @BeforeAll
    fun init() {
        webClient = MockMvcWebTestClient.bindTo(mockMvc).build()
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
//    @WithUserDetails()
    fun givenArticles_whenGetArticles_thenStatus200andJsonArray() {
        // prepare
        val now = LocalDateTime.now();
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        // test
        val response = webClient
//            .mutateWith(mockUser("admin").password("admin").roles("ADMIN"))
//            .mutate().defaultCookie(org.iproduct.spring.restmvc.RestMvcBootApplicationTests.AUTH_TOKEN, authToken)
//            .build()
            .get().uri("/api/blogs").accept(MediaType.APPLICATION_JSON)
            .exchange()

        response
            .expectAll({ response ->
                log().info(
                    response.returnResult(typeReference<FluxExchangeResult<String>>()).toString()
                )
            })
            .expectStatus().isOk
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
            .json(mapper.writeValueAsString(listOf(spring5Blog.toBlogDetailsView(), spring43Blog.toBlogDetailsView())))

        // verify repo method called
        verify { blogsRepository.findAll() }
        confirmVerified(blogsRepository) // no other methods were called
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun givenArticles_whenGetArticles_thenStatus200andJsonArray2() {
        // prepare
        val now = LocalDateTime.now();
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        // test
        webClient
            .get()
            .uri("/api/blogs").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBodyList<BlogDetailsView>()
            .hasSize(2)
            .contains(spring5Blog.toBlogDetailsView())
            .contains(spring43Blog.toBlogDetailsView())

        // verify repo method called
        verify { blogsRepository.findAll() }
        confirmVerified(blogsRepository) // no other methods were called
    }
}

package course.kotlin.spring.web

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
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.every
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@AutoConfigureWebTestClient
//@WebFluxTest(controllers = [BlogsRestController::class, UsersRestController::class])
//@AutoConfigureWebTestClient
@ActiveProfiles("test")
class HttpControllersTestsWebTestClient(@Autowired private val webClient: WebTestClient) {

    @MockkBean
    private lateinit var usersRepository: UsersRepository

    @MockkBean
    private lateinit var blogsRepository: BlogsRepository

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
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

//        val typeRef = typeReference<List<BlogDetailsView>>()
        response
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
    }

    @Test
    fun givenArticles_whenGetArticles_thenStatus200andJsonArray2() {
        val now = LocalDateTime.now();
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&", id = 1)
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen, id = 1)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen, id = 2)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        webClient
            .get().uri("/api/blogs").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBodyList<BlogDetailsView>()
            .hasSize(2)
            .contains(spring5Blog.toBlogDetailsView())
            .contains(spring43Blog.toBlogDetailsView())
    }
}

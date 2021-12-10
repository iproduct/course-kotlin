package course.kotlin.spring

import com.ninjasquad.springmockk.MockkBean
import course.kotlin.spring.dao.BlogsRepository
import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.extensions.log
import course.kotlin.spring.model.Blog
import course.kotlin.spring.model.User
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import org.assertj.core.api.BDDAssumptions.given
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.collection.IsIterableContainingInAnyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
//@EnableAutoConfiguration(exclude=[SecurityAutoConfiguration::class])
//@WebMvcTest
//@ExtendWith(SpringExtension::class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class HttpControllersTestsWebTestClient(@Autowired val webClient: WebTestClient) {

    @SpyK
    private lateinit var usersRepository: UsersRepository

    @SpyK
    private lateinit var blogsRepository: BlogsRepository

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    @WithMockUser(roles = ["ADMIN"])
    @Throws(Exception::class)
    fun givenArticles_whenGetArticles_thenStatus200andJsonArray() {
        val juergen = User("springjuergen", "Juergen", "Hoeller", "jurgen123&")
        val spring5Blog = Blog("Spring Framework 5.0 goes GA", "Dear Spring community ...", juergen)
        val spring43Blog = Blog("Spring Framework 4.3 goes GA", "Dear Spring community ...", juergen)
        every { blogsRepository.findAll() } returns listOf(spring5Blog, spring43Blog)

        var exchangeMutator: Any
        val response = webClient //        		.mutateWith(mockUser("admin").password("admin").roles("ADMIN"))
//            .mutate().defaultCookie(org.iproduct.spring.restmvc.RestMvcBootApplicationTests.AUTH_TOKEN, authToken)
//            .build()
            .get().uri("/api/articles").accept(MediaType.APPLICATION_JSON)
            .exchange()

//        log.info(">>>>> Result: {}", response.returnResult(String.class));
        response.expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("\$.[0].author.username").isEqualTo(juergen.username)
            .jsonPath("\$.[0].slug").isEqualTo(spring5Blog.slug)
            .jsonPath("\$.[1].author.username").isEqualTo(juergen.username)
            .jsonPath("\$.[1].slug").isEqualTo(spring43Blog.slug)
            .jsonPath("\$").isArray
            .jsonPath("\$.length").isEqualTo(2)


        response.expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$").value(
                containsInAnyOrder(spring5Blog, spring43Blog)
            )
            .jsonPath("$.length()").value(greaterThanOrEqualTo(2))

        response.returnResult<List<Blog>>().responseBody.subscribe {
            log().info(">>> {}", it)
        }
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

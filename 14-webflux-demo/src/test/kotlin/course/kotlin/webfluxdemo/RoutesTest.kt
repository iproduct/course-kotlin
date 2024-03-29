package course.kotlin.webfluxdemo

import course.kotlin.webfluxdemo.web.SimpleRouter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

class RoutesTest {

    lateinit var client: WebTestClient

    @BeforeEach
    fun init() {
        this.client = WebTestClient.bindToRouterFunction(SimpleRouter().route()).build()
    }


    @Test
    fun whenRequestToRoute_thenStatusShouldBeOk() {
        client.get()
                .uri("/route")
                .exchange()
                .expectStatus().isOk
    }


    @Test
    fun whenRequestToRoute_thenBodyShouldContainArray123() {
        client.get()
                .uri("/route")
                .exchange()
                .expectBody()
                .json("[1, 2, 3]")
    }
}

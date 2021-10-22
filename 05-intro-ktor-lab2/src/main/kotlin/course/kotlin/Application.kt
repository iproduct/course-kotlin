package course.kotlin


import course.kotlin.dao.InMemoryProductRepository
import course.kotlin.dao.ProductRepository
import course.kotlin.model.Product
import course.kotlin.plugins.configureHTTP
import course.kotlin.plugins.configureMonitoring
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

private val productRepository: ProductRepository = InMemoryProductRepository(
    listOf(
        Product(1, "Laptop Computer", 1500.0),
        Product(2, "Computer Mouse", 25.7),
        Product(3, "Wireless Keyboard", 35.2),
        Product(4, "Whiteboard Markers", 4.7),
    )
)

val Application.productRepo: ProductRepository
    get() = productRepository

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", watchPaths = listOf("classes", "resource")) {
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}

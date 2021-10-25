package course.kotlin


import course.kotlin.dao.*
import course.kotlin.model.Product
import course.kotlin.plugins.configureHTTP
import course.kotlin.plugins.configureMonitoring
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.atomic.AtomicInteger

private val productRepository: Repository<Int, Product> = InMemoryRepository(
    listOf(
        Product("Laptop Computer", 1500.0),
        Product("Computer Mouse", 25.7),
        Product("Wireless Keyboard", 35.2),
        Product("Whiteboard Markers", 4.7),
    ),
    object: IdGenerator<Int> {
        private val idSequence = AtomicInteger()
        override fun getNextId(): Int = idSequence.incrementAndGet()
    }
)

val Application.productRepo
    get() = productRepository

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", watchPaths = listOf("classes", "resource")) {
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}

package course.kotlin


import course.kotlin.dao.*
import course.kotlin.fielddelegate.FieldPropertyDelegate
import course.kotlin.model.Product
import course.kotlin.plugins.configureHTTP
import course.kotlin.plugins.configureMonitoring
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.*
import java.util.concurrent.atomic.AtomicInteger

typealias ProductRepository = Repository<Int, Product>

private val productRepository: ProductRepository = InMemoryRepository(
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

val PipelineContext<Unit, ApplicationCall>.productRepo: ProductRepository by FieldPropertyDelegate({
    InMemoryRepository(
        listOf(
            Product("${this.context.request.path()}", 1500.0),
        ),
        object: IdGenerator<Int> {
            private val idSequence = AtomicInteger()
            override fun getNextId(): Int = idSequence.incrementAndGet()
        }
    )
})

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", watchPaths = listOf("classes", "resource")) {
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
